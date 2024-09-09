package plus.axz.article.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.article.ArticleInfoControllerApi;
import plus.axz.article.service.ArticleInfoService;
import plus.axz.model.article.dtos.ArticleInfoDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 文章详情
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/article")
public class ArticleInfoController implements ArticleInfoControllerApi {

    private final ArticleInfoService articleInfoService;

    @PostMapping("/load_article_info")
    @Override
    public ResponseResult<?> loadArticleInfo(@RequestBody ArticleInfoDto dto) {
        return articleInfoService.loadArticleInfo(dto);
    }

    // 行为业务展示
    @PostMapping("/load_article_behavior")
    @Override
    public ResponseResult<?> loadArticleBehavior(@RequestBody ArticleInfoDto dto) {
        return articleInfoService.loadArticleBehavior(dto);
    }
}
