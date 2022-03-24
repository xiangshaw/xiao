package plus.axz.user.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserRealnameControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.user.service.UserRealnameService;

/**
 * @author xiaoxiang
 * @date 2022年03月24日
 * @particulars 查询用户认证
 */
@RestController
@RequestMapping("/api/v1/auth")
public class UserRealnameController implements UserRealnameControllerApi {
    @Autowired
    private UserRealnameService userRealnameService;

    @Override
    @PostMapping("/list")
    public ResponseResult loadListByStatus(@RequestBody AuthDto dto) {
        return userRealnameService.loadListByStatus(dto);
    }

    @Override
    public ResponseResult authPass(AuthDto dto) {
        return null;
    }

    @Override
    public ResponseResult authFail(AuthDto dto) {
        return null;
    }
}
