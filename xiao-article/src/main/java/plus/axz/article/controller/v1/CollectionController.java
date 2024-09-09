package plus.axz.article.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.article.CollectionControllerApi;
import plus.axz.article.service.CollectionService;
import plus.axz.model.article.dtos.CollectionDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 收藏
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/collection_behavior")
public class CollectionController implements CollectionControllerApi {

    private final CollectionService collectionService;

    @PostMapping
    @Override
    public ResponseResult<?> collectionBehavior(@RequestBody CollectionDto dto) {
        return collectionService.collectionBehavior(dto);
    }
}
