package plus.axz.api.admin;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import plus.axz.model.admin.dtos.AdUserDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description admin登录功能
 */
public interface LoginControllerApi {
    /**
     * admin登录功能
     */
    @ApiOperation("admin登录")
    ResponseResult<?> login(@RequestBody AdUserDto dto);
}
