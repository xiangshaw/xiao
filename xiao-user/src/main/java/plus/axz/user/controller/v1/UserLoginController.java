package plus.axz.user.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserLoginControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.LoginDto;
import plus.axz.user.service.UserLoginService;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars 登录控制器
 */
@RestController
@RequestMapping("/api/v1/login")
public class UserLoginController implements UserLoginControllerApi {
    @Autowired
    private UserLoginService userloginser;

    @PostMapping("/login_auth")
    @Override
    public ResponseResult login(@RequestBody LoginDto dto) {
        return userloginser.login(dto);
    }
}
