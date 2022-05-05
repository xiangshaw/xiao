package plus.axz.article.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.article.ArticleControllerApi;
import plus.axz.article.service.ArticleService;
import plus.axz.model.article.pojos.Article;

/**
 * @author xiaoxiang
 * @date 2022年05月03日
 * @particulars 文章信息
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController implements ArticleControllerApi {

    @Autowired
    private ArticleService articleService;
    @Override
    @PostMapping("/save")
    public Article saveArticle(@RequestBody Article article) {
        articleService.save(article);
        return article;
    }
}
