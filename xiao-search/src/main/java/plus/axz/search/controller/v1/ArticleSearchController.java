package plus.axz.search.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.search.ArticleSearchControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.search.service.ArticleSearchService;

import java.io.IOException;

/**
 * @author xiaoxiang
 * description es文章搜索
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/article/search")
public class ArticleSearchController implements ArticleSearchControllerApi {

    private final ArticleSearchService articleSearchService;
    @Override
    @PostMapping("/search")
    public ResponseResult<?> search(@RequestBody UserSearchDto dto) throws IOException {
        return articleSearchService.search(dto);
    }
}
