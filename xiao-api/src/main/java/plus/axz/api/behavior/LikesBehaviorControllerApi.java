package plus.axz.api.behavior;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.behavior.dtos.LikesBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
public interface LikesBehaviorControllerApi {
    @ApiOperation("保存点赞行为")
    ResponseResult like(LikesBehaviorDto dto);
}
