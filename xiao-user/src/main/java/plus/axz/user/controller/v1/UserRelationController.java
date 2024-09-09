package plus.axz.user.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserRelationControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.UserRelationDto;
import plus.axz.user.service.UserRelationService;

/**
 * @author xiaoxiang
 * description 关注作者或取消
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserRelationController implements UserRelationControllerApi {

    private final UserRelationService userRelationService;
    @Override
    @PostMapping("/user_follow")
    public ResponseResult<?> follow(@RequestBody UserRelationDto dto){
        return userRelationService.follow(dto);
    }
}
