package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.UserRelationDto;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars
 */
public interface UserRelationControllerApi {
    @ApiOperation("关注或取消关注")
    ResponseResult follow(UserRelationDto dto);
}
