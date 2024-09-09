package plus.axz.article.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.article.ArticleHomeControllerApi;
import plus.axz.article.service.ArticleService;
import plus.axz.common.constants.article.ArticleConstans;
import plus.axz.model.article.dtos.ArticleHomeDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 首页文章加载
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController implements ArticleHomeControllerApi {

    private final ArticleService articleService;

    @PostMapping("/load")
    @Override
    public ResponseResult<?> load(@RequestBody ArticleHomeDto dto) {
        // return articleService.load(dto, ArticleConstans.LOADTYPE_LOAD_MORE);
        return articleService.load2(dto, ArticleConstans.LOADTYPE_LOAD_MORE, true);
    }

    @PostMapping("/loadmore")
    @Override
    public ResponseResult<?> loadMore(@RequestBody ArticleHomeDto dto) {
        return articleService.load(dto, ArticleConstans.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadnew")
    @Override
    public ResponseResult<?> loadNew(@RequestBody ArticleHomeDto dto) {
        return articleService.load(dto, ArticleConstans.LOADTYPE_LOAD_NEW);
    }
}
