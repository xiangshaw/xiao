package plus.axz.article.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.article.ArticleConfigControllerApi;
import plus.axz.article.service.ArticleConfigService;
import plus.axz.model.article.pojos.ArticleConfig;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

/**
 * @author xiaoxiang
 * description 文章配置
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/article_config")
public class ArticleConfigController implements ArticleConfigControllerApi {

    private final ArticleConfigService articleConfigService;

    @PostMapping("/save")
    @Override
    public ResponseResult<?> saveArticleConfig(@RequestBody ArticleConfig articleConfig) {
        articleConfigService.save(articleConfig);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
