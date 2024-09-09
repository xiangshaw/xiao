package plus.axz.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import plus.axz.model.article.pojos.Article;
import plus.axz.model.article.pojos.ArticleConfig;
import plus.axz.model.article.pojos.ArticleContent;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 在文章审核成功以后需要在app的article库中新增文章数据
 * 1 保存文章信息    article，需要返回当前文章，并且需要获取保存后获取到的主键
 * 2 保存文章配置信息    article_config
 * 3 保存文章内容    article_content
 * 4 在保存文章的时候需要关联作者，需要根据名称查询作者信息
 */
@FeignClient("xiao-article")
public interface ArticleFeign {
    //返回值就是需要保存的数据
    @PostMapping("/api/v1/article/save")
    Article saveArticle(Article article);

    @PostMapping("/api/v1/article_config/save")
    ResponseResult<ArticleConfig> saveArticleConfig(ArticleConfig articleConfig);

    @PostMapping("/api/v1/article_content/save")
    ResponseResult<ArticleContent> saveArticleContent(ArticleContent apArticleContent);

    /*根据名称去查询*/
    @GetMapping("/api/v1/author/findByName/{name}")
    Author selectAuthorByName(@PathVariable("name") String name);
}
