package plus.axz.article.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.article.ArticleContentControllerApi;
import plus.axz.article.service.ArticleContentService;
import plus.axz.model.article.pojos.ArticleContent;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

/**
 * @author xiaoxiang
 * @date 2022年05月03日
 * @particulars 文章内容
 */
@RestController
@RequestMapping("/api/v1/article_content")
public class ArticleContentController implements ArticleContentControllerApi {

    @Autowired
    private ArticleContentService articleContentService;
    @Override
    @PostMapping("/save")
    public ResponseResult saveArticleContent(@RequestBody ArticleContent articleContent) {
        articleContentService.save(articleContent);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
