package plus.axz.behavior.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.behavior.CollectionBehaviorControllerApi;
import plus.axz.behavior.service.CollectionBehaviorService;
import plus.axz.model.behavior.dtos.CollectionBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 收藏行为
 */
@RestController
@RequestMapping("/api/v1/collection_behavior")
public class CollectionBehaviorController implements CollectionBehaviorControllerApi {
    @Autowired
    private CollectionBehaviorService collectionBehaviorService;

    @PostMapping
    @Override
    public ResponseResult collectionBehavior(@RequestBody CollectionBehaviorDto dto) {
        return collectionBehaviorService.collectionBehavior(dto);
    }
}
