package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmUserDto;

/**
 * @author xiaoxiang
 * @date 2022年04月01日
 * @particulars 自媒体用户登录接口
 */
public interface LoginControllerApi {

    @ApiOperation("自媒体用户登录")
    public ResponseResult login(WmUserDto dto);
}
