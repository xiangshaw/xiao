package plus.axz.api.admin;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import plus.axz.model.admin.dtos.AdUserDto;
import plus.axz.model.common.dtos.ResponseResult;

public interface LoginControllerApi {
    /**
     * admin登录功能
     * @param dto
     * @return
     */
    @ApiOperation("admin登录")
    public ResponseResult login(@RequestBody AdUserDto dto);
}
