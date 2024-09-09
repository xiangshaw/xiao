package plus.axz.article.controller.v1;

import lombok.RequiredArgsConstructor;
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
 * description 文章内容
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/article_content")
public class ArticleContentController implements ArticleContentControllerApi {

    private final ArticleContentService articleContentService;

    @Override
    @PostMapping("/save")
    public ResponseResult<?> saveArticleContent(@RequestBody ArticleContent articleContent) {
        articleContentService.save(articleContent);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
