package plus.axz.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import plus.axz.article.mapper.ArticleMapper;
import plus.axz.article.service.ArticleService;
import plus.axz.common.constants.article.ArticleConstans;
import plus.axz.model.article.dtos.ArticleHomeDto;
import plus.axz.model.article.mess.ArticleVisitStreamMess;
import plus.axz.model.article.pojos.Article;
import plus.axz.model.article.vo.HotArticleVo;
import plus.axz.model.common.dtos.ResponseResult;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoxiang
 * description 文章信息 -- 首页加载
 */
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    // 单页最大加载的数字
    private static final short MAX_PAGE_SIZE = 50;

    private final ArticleMapper articleMapper;

    @Value("${fdfs.url}")
    private String fileServerUrl;

    @Override
    public ResponseResult<?> load(ArticleHomeDto dto, Short loadType) {
        //1.检验参数
        Integer size = dto.getSize();
        if (size == null || size == 0) {
            size = 10;
        }
        // size小于50的话就取size,大于50则取50这个值，请求数据不超过50条
        size = Math.min(size, MAX_PAGE_SIZE);
        // 设置分页的条数
        dto.setSize(size);
        //类型参数校验
        if (!loadType.equals(ArticleConstans.LOADTYPE_LOAD_MORE) && !loadType.equals(ArticleConstans.LOADTYPE_LOAD_NEW)) {
            // 加载更多
            loadType = ArticleConstans.LOADTYPE_LOAD_MORE;
        }
        // 文章频道校验
        if (StringUtils.isEmpty(dto.getTagId())) {
            // tag为空，给 __all__ 值
            dto.setTagId(ArticleConstans.DEFAULT_TAG);
        }
        //时间校验，如果为空，给个当前时间
        if (dto.getMaxBehotTime() == null) {
            dto.setMaxBehotTime(new Date());
        }
        if (dto.getMinBehotTime() == null) {
            dto.setMinBehotTime(new Date());
        }
        //2.查询数据
        List<Article> apArticles = articleMapper.loadArticleList(dto, loadType);
        //3.结果封装
        ResponseResult<?> responseResult = ResponseResult.okResult(apArticles);
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    private final StringRedisTemplate redisTemplate;

    /**
     * 重新计算文章的分值，更新到数据库和缓存中
     * 文章微服务接收到kafka stream 实时流式处理后的结果消息后，需要做两件事情：
     * 更新MySQL数据库文章表的相关行为的分值
     * 更新Redis缓存中热点文章
     * 重新计算文章的分值
     */
    @Override
    public void updateArticle(ArticleVisitStreamMess mess) {
        //1.查询文章
        Article article = getById(mess.getArticleId());

        if (article == null) {
            log.error("当前文章不存在");
        }
        //2.修改文章
        //阅读数量
        int view = (int) (article.getViews() == null ? mess.getView() : mess.getView() + article.getViews());
        // 设置到数据库中
        article.setViews(view);
        //点赞数量
        int like = (int) (article.getLikes() == null ? mess.getLike() : mess.getLike() + article.getLikes());
        article.setLikes(like);
        //评论数量
        int comment = (int) (article.getComment() == null ? mess.getComment() : mess.getComment() + article.getComment());
        article.setComment(comment);
        //收藏数量
        int collection = (int) (article.getCollection() == null ? mess.getCollect() : mess.getCollect() + article.getCollection());
        article.setCollection(collection);
        //修改数据
        updateById(article);

        //3.计算文章的分值  权重*3
        Integer score = computeScore(article);
        score = score * 3;
        //4.查询redis  替换分值较低的数据
        String articleListStr = redisTemplate.opsForValue().get(ArticleConstans.HOT_ARTICLE_FIRST_PAGE + article.getTagId());
        updateArticleCache(article, score, articleListStr);

        //5.更新推荐缓存中的数据
        String articleListStrAll = redisTemplate.opsForValue().get(ArticleConstans.HOT_ARTICLE_FIRST_PAGE + ArticleConstans.DEFAULT_TAG);
        updateArticleCache(article, score, articleListStrAll);

    }

    /**
     * 更新文章的分值
     */
    private void updateArticleCache(Article article, Integer score, String articleListStr) {
        boolean flag = false;
        if (StringUtils.isNotBlank(articleListStr)) {
            List<HotArticleVo> hotArticleVoList = JSON.parseArray(articleListStr, HotArticleVo.class);
            //4.1.如果当前缓存中有当前文章，则更新分值
            for (HotArticleVo hotArticleVo : hotArticleVoList) {
                if (hotArticleVo.getId().equals(article.getId())) {
                    hotArticleVo.setScore(score);
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                //4.2.如果缓存中没有当前文章，获取缓存中分值较低的数据进行对比
                if (hotArticleVoList.size() >= 30) {
                    //排序
                    hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
                    //分值最低的文章数据
                    HotArticleVo hotArticleVo = hotArticleVoList.get(hotArticleVoList.size() - 1);
                    if (hotArticleVo.getScore() < score) {
                        //先删除，再添加
                        hotArticleVoList.remove(hotArticleVo);
                        // 新创建热点文章
                        HotArticleVo hot = new HotArticleVo();
                        // 把属性拷贝过去
                        BeanUtils.copyProperties(article, hot);
                        // 分值设置进去
                        hotArticleVo.setScore(score);
                        // 最后添加到集合中
                        hotArticleVoList.add(hot);
                    }
                } else {
                    // 当前缓存中不满足30条数据，直接把当前文章添加到缓存
                    HotArticleVo hotArticleVo = new HotArticleVo();
                    BeanUtils.copyProperties(article, hotArticleVo);
                    hotArticleVo.setScore(score);
                    hotArticleVoList.add(hotArticleVo);
                }
            }
            //更新数据到缓存中
            redisTemplate.opsForValue().set(ArticleConstans.HOT_ARTICLE_FIRST_PAGE + article.getTagId(),
                    JSON.toJSONString(hotArticleVoList));

        }
    }

    /**
     * 计算分值
     */
    private Integer computeScore(Article article) {
        int score = 0;
        if (article.getLikes() != null) {
            score += article.getLikes() * ArticleConstans.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if (article.getViews() != null) {
            score += article.getViews();
        }
        if (article.getComment() != null) {
            score += article.getComment() * ArticleConstans.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if (article.getCollection() != null) {
            score += article.getCollection() * ArticleConstans.HOT_ARTICLE_COLLECTION_WEIGHT;
        }
        return score;
    }

    @Override
    public ResponseResult<?> load2(ArticleHomeDto dto, Short loadtypeLoadMore, boolean firstPage) {
        // 判断是否为首页，从缓存中拿数据
        if (firstPage) {
            // tag就是频道Id
            String jsonStr = redisTemplate.opsForValue().get(ArticleConstans.HOT_ARTICLE_FIRST_PAGE + dto.getTagId());
            if (StringUtils.isNotBlank(jsonStr)) {
                List<HotArticleVo> hotArticleVoList = JSON.parseArray(jsonStr, HotArticleVo.class);
                if (!hotArticleVoList.isEmpty() && hotArticleVoList.size() > 0) {
                    ResponseResult<?> responseResult = ResponseResult.okResult(hotArticleVoList);
                    responseResult.setHost(fileServerUrl);
                    return responseResult;
                }
            }
        }
        // 不是第一页，正常查数据
        return load(dto, loadtypeLoadMore);
    }
}
