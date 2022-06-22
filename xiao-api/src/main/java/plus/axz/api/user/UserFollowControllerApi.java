package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.user.pojos.UserFollow;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
public interface UserFollowControllerApi {
    //远程接口
    @ApiOperation("根据用户id和关注作者的id查询")
    public UserFollow findByUserIdAndFollowId(Integer userId, Integer followId);
}
