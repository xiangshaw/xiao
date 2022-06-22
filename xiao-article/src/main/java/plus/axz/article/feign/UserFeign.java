package plus.axz.article.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plus.axz.model.user.pojos.UserFollow;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 用户远程接口
 */
@FeignClient("xiao-user")
public interface UserFeign {

    @GetMapping("/api/v1/user_follow/one")
    UserFollow findByUserIdAndFollowId(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId);
}
