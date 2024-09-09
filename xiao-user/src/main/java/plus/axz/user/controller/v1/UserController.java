package plus.axz.user.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plus.axz.api.user.UserControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.pojos.User;
import plus.axz.user.service.UserService;

/**
 * @author xiaoxiang
 * description 根据id查询app用户信息
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController implements UserControllerApi {

    private final UserService userService;

    @GetMapping("/{id}")
    @Override
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    // 用户注册
    @PostMapping("/register")
    @Override
    public ResponseResult<?> UserRegister(@RequestBody User user) {
        return userService.userRegister(user);
    }
}
