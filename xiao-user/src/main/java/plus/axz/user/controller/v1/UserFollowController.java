package plus.axz.user.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserFollowControllerApi;
import plus.axz.model.user.pojos.UserFollow;
import plus.axz.user.service.UserFollowService;

/**
 * @author xiaoxiang
 * description 远程接口-用户关注信息
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user_follow")
public class UserFollowController implements UserFollowControllerApi {

    private final UserFollowService userFollowService;

    @GetMapping("/one")
    @Override
    public UserFollow findByUserIdAndFollowId(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId) {
        return userFollowService.getOne(Wrappers.<UserFollow>lambdaQuery()
                .eq(UserFollow::getUserId,userId)
                .eq(UserFollow::getFollowId,followId));
    }
}
