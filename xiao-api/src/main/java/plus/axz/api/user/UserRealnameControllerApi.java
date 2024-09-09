package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.AuthDto;

/**
 * @author xiaoxiang
 * description 用户实名认证接口
 */
public interface UserRealnameControllerApi {
    @ApiOperation("分页查询用户列表")
    ResponseResult<?> loadListByStatus(AuthDto dto);

    @ApiOperation("认证审核通过")
    ResponseResult<?> authPass(AuthDto dto);

    @ApiOperation("认证审核失败")
    ResponseResult<?> authFail(AuthDto dto);
}
