package plus.axz.user.service;

import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.LoginDto;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars app端登录
 */
public interface UserLoginService {
    public ResponseResult login(LoginDto dto);
}
