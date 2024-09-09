package plus.axz.api.behavior;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.behavior.dtos.ReadBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 保存或更新阅读行为
 */
public interface ReadBehaviorControllerApi {
    @ApiOperation("保存或更新阅读行为")
    ResponseResult<?> readBehavior(ReadBehaviorDto dto);
}
