package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.user.pojos.UserFollow;

/**
 * @author xiaoxiang
 * description
 */
public interface UserFollowControllerApi {
    //远程接口
    @ApiOperation("根据用户id和关注作者的id查询")
    UserFollow findByUserIdAndFollowId(Integer userId, Integer followId);
}
