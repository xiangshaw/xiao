package plus.axz.behavior.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.behavior.UnlikeBehaviorControllerApi;
import plus.axz.behavior.service.UnlikesBehaviorService;
import plus.axz.model.behavior.dtos.UnLikesBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
@RestController
@RequestMapping("/api/v1/unlikes_behavior")
public class UnlikesBehaviorController implements UnlikeBehaviorControllerApi {
    @Autowired
    private UnlikesBehaviorService unlikesBehaviorService;

    @PostMapping
    @Override
    public ResponseResult unlikeBehavior(@RequestBody UnLikesBehaviorDto dto) {
        return unlikesBehaviorService.unlikeBehavior(dto);
    }
}
