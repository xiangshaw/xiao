package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.LoginDto;
import plus.axz.model.user.dtos.ULoginDto;

/**
 * @author xiaoxiang
 * @date 2022年06月19日
 * @particulars app端登录 准备
 */
public interface UserLoginControllerApi {
    @ApiOperation("app端登录")
    public ResponseResult login(LoginDto dto);

    @ApiOperation("web端登录")
    public ResponseResult ulogin(ULoginDto dto);
}
