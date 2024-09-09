package plus.axz.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.axz.article.feign.AdminFeign;
import plus.axz.article.mapper.ArticleMapper;
import plus.axz.article.service.HotArticlesService;
import plus.axz.common.constants.article.ArticleConstans;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.article.pojos.Article;
import plus.axz.model.article.vo.HotArticleVo;
import plus.axz.model.common.dtos.ResponseResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoxiang
 * description 计算热文章
 */
@RequiredArgsConstructor
@Service
@Transactional
public class HotArticleServiceImpl implements HotArticlesService {

    private final ArticleMapper articleMapper;

    // 定时 计算热点文章数据
    @Override
    public void computeHotArticle() {
        // 数据库要保证有前几天的数据
        // 1.查询前5天的文章数据（代码释义 拿到当前时间 减去 5 天，给个格式，返回的就是时间参数）
        String s = DateTime.now().minusDays(5).toString("yyyy-MM-dd 00:00:00");
        // gt 大于 发布时间大于前五天的点 （拿到前5天的文章数据）
        List<Article> articles = articleMapper.selectList(Wrappers.<Article>lambdaQuery().gt(Article::getPublishTime, s));
        // 2.计算文章分值（根据阅读、点赞、评论以及收藏的量）
        List<HotArticleVo> hotArticleVoList = computeHotArticle(articles);
        // 3.为每一个频道标签热点较高的30条文章
        cacheTagToRedis(hotArticleVoList);
    }

    /**
     * 计算文章分值
     */
    private List<HotArticleVo> computeHotArticle(List<Article> articles) {
        List<HotArticleVo> resultList = new ArrayList<>();
        HotArticleVo hotArticleVo = null;
        if (articles != null && !articles.isEmpty()) {
            for (Article article : articles) {
                hotArticleVo = new HotArticleVo();
                // article对象 拷贝到 hotArticleVo
                BeanUtils.copyProperties(article, hotArticleVo);
                // 某一篇文章的分值
                Integer score = computeScore(article);
                // score 封装到 hotArticleVo中
                hotArticleVo.setScore(score);
                // 将一个个对象装入集合中
                resultList.add(hotArticleVo);
            }
        }
        // 返回对象
        return resultList;
    }

    /**
     * 计算某一个文章的分值
     */
    private Integer computeScore(Article article) {
        // 默认分值0
        int score = 0;
        if (article.getLikes() != null) {
            // 喜欢量 的权重
            score += article.getLikes() * ArticleConstans.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if (article.getViews() != null) {
            // 浏览量 的权重
            score += article.getViews();
        }
        if (article.getComment() != null) {
            // 评论量 的权重
            score += article.getComment() * ArticleConstans.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if (article.getCollection() != null) {
            // 收藏量 的权重
            score += article.getCollection() * ArticleConstans.HOT_ARTICLE_COLLECTION_WEIGHT;
        }
        return score;
    }

    private final AdminFeign adminFeign;

    private final StringRedisTemplate redisTemplate;

    /**
     * 给每一个频道缓存分值较高的30条数据
     */
    private void cacheTagToRedis(List<HotArticleVo> hotArticleVoList) {
        // 1.查询所有频道标签
        ResponseResult<?> responseResult = adminFeign.selectAllTag();
        // 拿到所有频道标签信息 （先将JSON数据转为对象）
        List<Tag> tags = JSON.parseArray(JSON.toJSONString(responseResult.getData()), Tag.class);
        // 2.检索出频道对应的文章列表，给每个频道 分值较高的 存30条数据 到Redis
        if (hotArticleVoList != null && !hotArticleVoList.isEmpty()) {
            for (Tag tag : tags) {
                // 筛选后的数据 （筛选 hotArticleVoList文章中 哪些属于 当前频道标签数据，，查询出当前频道所有数据）
                List<HotArticleVo> hotArticleVos = hotArticleVoList.stream().filter(x -> x.getTagId().equals(tag.getId())).collect(Collectors.toList());
                // 3.给每个频道进行缓存 （频道标签名称 HOT_ARTICLE_FIRST_PAGE + 频道标签的ID值
                sortAndCache(hotArticleVos, ArticleConstans.HOT_ARTICLE_FIRST_PAGE + tag.getId());
            }
        }
        // 4.给推荐频道缓存30条数据_all_  所有文章排序之后的前30条存入Redis （key 是频道标签
        sortAndCache(hotArticleVoList, ArticleConstans.HOT_ARTICLE_FIRST_PAGE + ArticleConstans.DEFAULT_TAG);
    }

    /**
     * 排序并缓存
     */
    private void sortAndCache(List<HotArticleVo> hotArticleVos, String s) {
        // sort排序
        hotArticleVos.sort(new Comparator<HotArticleVo>() {
            @Override
            public int compare(HotArticleVo o1, HotArticleVo o2) {
                return o2.getScore().compareTo(o1.getScore());
            }
        });
        // 大于30条数据，只要30条
        if (hotArticleVos.size() > 30) {
            // 再存入集合中
            hotArticleVos = hotArticleVos.subList(0, 30);
        }
        // hotArticleVos.size==0 直接存入redis缓存 （key 就是频道标签ID
        redisTemplate.opsForValue().set(s, JSON.toJSONString(hotArticleVos));
    }
}
