package plus.axz.behavior.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.behavior.LikesBehaviorControllerApi;
import plus.axz.behavior.service.LikesBehaviorService;
import plus.axz.model.behavior.dtos.LikesBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
@RestController
@RequestMapping("/api/v1/likes_behavior")
public class LikesBehaviorController implements LikesBehaviorControllerApi {
    @Autowired
    private LikesBehaviorService likesBehaviorService;
    // 点赞行为
    @Override
    @PostMapping
    public ResponseResult like(@RequestBody LikesBehaviorDto dto) {
        return likesBehaviorService.like(dto);
    }
}
