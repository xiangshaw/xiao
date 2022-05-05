package plus.axz.article.controller.v1;


import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2022年05月03日
 * @particulars 文章配置
 */
@RestController
@RequestMapping("/api/v1/article_config")
public class ArticleConfigController implements ArticleConfigControllerApi {
    @Autowired
    private ArticleConfigService articleConfigService;
    @PostMapping("/save")
    @Override
    public ResponseResult saveArticleConfig(@RequestBody ArticleConfig ArticleConfig) {
        articleConfigService.save(ArticleConfig);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
