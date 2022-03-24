package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.AuthDto;

public interface UserRealnameControllerApi {
    /**
     * 按照状态分页查询用户列表
     */
    @ApiOperation("分页查询用户列表")
    public ResponseResult loadListByStatus(AuthDto dto);

    /**
     * 审核通过
     */
    @ApiOperation("认证审核通过")
    public ResponseResult authPass(AuthDto dto);

    /**
     * 审核失败
     */
    @ApiOperation("认证审核失败")
    public ResponseResult authFail(AuthDto dto);
}
