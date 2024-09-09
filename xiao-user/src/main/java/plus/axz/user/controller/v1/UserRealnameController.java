package plus.axz.user.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserRealnameControllerApi;
import plus.axz.common.constants.user.UserConstants;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.user.service.UserRealnameService;

/**
 * @author xiaoxiang
 * description 查询用户认证
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserRealnameController implements UserRealnameControllerApi {

    private final UserRealnameService userRealnameService;

    @Override
    @PostMapping("/list")
    public ResponseResult<?> loadListByStatus(@RequestBody AuthDto dto) {
        return userRealnameService.loadListByStatus(dto);
    }

    /*审核成功*/
    @PostMapping("/authPass")
    @Override
    public ResponseResult<?> authPass(@RequestBody AuthDto dto) {
        return userRealnameService.updateStatusById(dto, UserConstants.PASS_AUTH);
    }

    /*审核失败*/
    @PostMapping("/authFail")
    @Override
    public ResponseResult<?> authFail(@RequestBody AuthDto dto) {
        return userRealnameService.updateStatusById(dto,UserConstants.FAIL_AUTH);
    }
}
