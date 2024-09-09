package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.UserRelationDto;

/**
 * @author xiaoxiang
 * description 关注接口
 */
public interface UserRelationControllerApi {
    @ApiOperation("关注或取消关注")
    ResponseResult<?> follow(UserRelationDto dto);
}
