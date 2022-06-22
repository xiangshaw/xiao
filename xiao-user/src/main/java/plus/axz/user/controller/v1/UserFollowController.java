package plus.axz.user.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserFollowControllerApi;
import plus.axz.model.user.pojos.UserFollow;
import plus.axz.user.service.UserFollowService;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 远程接口-用户关注信息
 */
@RestController
@RequestMapping("/api/v1/user_follow")
public class UserFollowController implements UserFollowControllerApi {
    @Autowired
    private UserFollowService userFollowService;

    @GetMapping("/one")
    @Override
    public UserFollow findByUserIdAndFollowId(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId) {
        return userFollowService.getOne(Wrappers.<UserFollow>lambdaQuery()
                .eq(UserFollow::getUserId,userId)
                .eq(UserFollow::getFollowId,followId));
    }
}
