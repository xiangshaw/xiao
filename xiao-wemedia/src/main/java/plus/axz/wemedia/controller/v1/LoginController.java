package plus.axz.wemedia.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.wemedia.LoginControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmUserDto;
import plus.axz.wemedia.service.WmUserService;

/**
 * @author xiaoxiang
 * description
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    private final WmUserService wmUserService;

    @PostMapping("/in")
    @Override
    public ResponseResult<?> login(@RequestBody WmUserDto dto) {
        return wmUserService.login(dto);
    }
}
