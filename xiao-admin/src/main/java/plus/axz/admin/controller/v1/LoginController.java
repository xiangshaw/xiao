package plus.axz.admin.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.admin.service.UserLoginService;
import plus.axz.api.admin.LoginControllerApi;
import plus.axz.model.admin.dtos.AdUserDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description admin登录
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login")
public class LoginController implements LoginControllerApi {

    private final UserLoginService userLoginService;

    @Override
    @PostMapping("/in")
    public ResponseResult<?> login(AdUserDto dto) {
        return userLoginService.login(dto);
    }
}
