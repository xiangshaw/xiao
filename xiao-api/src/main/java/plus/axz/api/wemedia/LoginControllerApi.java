package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmUserDto;

/**
 * @author xiaoxiang
 * description 自媒体用户登录接口
 */
public interface LoginControllerApi {
    @ApiOperation("自媒体用户登录")
    ResponseResult<?> login(WmUserDto dto);
}
