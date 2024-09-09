package plus.axz.user.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserLoginControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.LoginDto;
import plus.axz.model.user.dtos.ULoginDto;
import plus.axz.user.service.UserLoginService;

/**
 * @author xiaoxiang
 * description 登录控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login")
public class UserLoginController implements UserLoginControllerApi {

    private final UserLoginService userloginser;

    @PostMapping("/login_auth")
    @Override
    public ResponseResult<?> login(@RequestBody LoginDto dto) {
        return userloginser.login(dto);
    }

    @PostMapping("/in")
    @Override
    public ResponseResult<?> ulogin(@RequestBody ULoginDto dto) {
        return userloginser.ulogin(dto);
    }
}
