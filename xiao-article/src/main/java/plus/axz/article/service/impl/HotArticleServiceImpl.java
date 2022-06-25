package plus.axz.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2022年06月25日
 * @particulars 计算热文章
 */

@Service
@Transactional
public class HotArticleServiceImpl implements HotArticlesService {
    @Autowired
    private ArticleMapper articleMapper;

    // 定时 计算热点文章数据
    @Override
    public void computeHotArticle() {
        // 1.查询前5天的文章数据
        String s = DateTime.now().minusDays(5).toString("yyyy-MM-dd 00:00:00");
        // gt 大于 发布时间大于前五天的点
        List<Article> articles = articleMapper.selectList(Wrappers.<Article>lambdaQuery().gt(Article::getPublishTime, s));
        // 2.计算文章分值
        List<HotArticleVo> hotArticleVoList = computeHotArticle(articles);
        // 3.为每一个频道标签热点较高的30条文章
        cacheTagToRedis(hotArticleVoList);
    }

    /**
     * 计算文章分值
     * @param articles
     * @return java.util.List<plus.axz.model.article.pojos.Article>
     * @author xiaoxiang
     * @date 2022/6/25
     */
    private List<HotArticleVo> computeHotArticle(List<Article> articles) {
        List<HotArticleVo> resultList = new ArrayList<>();
        HotArticleVo hotArticleVo = null;
        if (articles != null && !articles.isEmpty()) {
            for (Article article : articles) {
                hotArticleVo = new HotArticleVo();
                BeanUtils.copyProperties(article, hotArticleVo);
                Integer score = computeScore(article);
                hotArticleVo.setScore(score);
                resultList.add(hotArticleVo);
            }
        }
        return resultList;
    }

    /**
     * 计算某一个文章的分值
     * @param article
     * @author xiaoxiang
     * @date 2022/6/25
     */
    private Integer computeScore(Article article) {
        // 默认分值0
        Integer score = 0;
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

    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 给每一个频道缓存分值较高的30条数据
     * @param hotArticleVoList
     * @author xiaoxiang
     * @date 2022/6/25
     */
    private void cacheTagToRedis(List<HotArticleVo> hotArticleVoList) {
        // 1.查询所有频道标签
        ResponseResult responseResult = adminFeign.selectAllTag();
        // 拿到所有频道标签信息
        List<Tag> tags = JSON.parseArray(JSON.toJSONString(responseResult.getData()), Tag.class);
        // 2.检索出频道对应的文章列表，给每个频道存入30条缓存数据
        if (hotArticleVoList != null && !hotArticleVoList.isEmpty()) {
            for (Tag tag : tags) {
                // 筛选后的数据
                List<HotArticleVo> hotArticleVos = hotArticleVoList.stream().filter(x -> x.getTagId().equals(tag.getId())).collect(Collectors.toList());
                // 3.给每个频道进行缓存
                sortAndCache(hotArticleVos, ArticleConstans.HOT_ARTICLE_FIRST_PAGE + tag.getId());
            }
        }
        // 4.给推荐频道缓存30条数据  所有文章排序之后的前30条
        sortAndCache(hotArticleVoList, ArticleConstans.HOT_ARTICLE_FIRST_PAGE + ArticleConstans.DEFAULT_TAG);
    }

    /**
     * 排序并缓存
     * @param hotArticleVos
     * @param s
     * @author xiaoxiang
     * @date 2022/6/25
     */
    private void sortAndCache(List<HotArticleVo> hotArticleVos, String s) {
        hotArticleVos.sort(new Comparator<HotArticleVo>() {
            @Override
            public int compare(HotArticleVo o1, HotArticleVo o2) {
                return o2.getScore().compareTo(o1.getScore());
            }
        });
        if (hotArticleVos.size() > 30) {
            hotArticleVos = hotArticleVos.subList(0, 30);
        }
        /*hotArticleVos.size==0存入redis缓存*/
        redisTemplate.opsForValue().set(s, JSON.toJSONString(hotArticleVos));
    }

}
