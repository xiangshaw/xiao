package plus.axz.user.service;

import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.LoginDto;
import plus.axz.model.user.dtos.ULoginDto;

/**
 * @author xiaoxiang
 * description app端登录
 */
public interface UserLoginService {
    ResponseResult<?> login(LoginDto dto);

    ResponseResult<?> ulogin(ULoginDto dto);
}
