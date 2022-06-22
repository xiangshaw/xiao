package plus.axz.comment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import plus.axz.model.user.pojos.User;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
@FeignClient("xiao-user")
public interface UserFeign {
    // 根据id查询app用户信息
    @GetMapping("/api/v1/user/{id}")
    User findUserById(@PathVariable("id") Integer id);
}
