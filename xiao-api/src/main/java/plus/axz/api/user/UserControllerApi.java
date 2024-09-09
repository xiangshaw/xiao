package plus.axz.api.user;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.pojos.User;

/**
 * @author xiaoxiang
 * description 评论中需要查询登录的用户信息，所以需要定义远程feign接口根据用户id获取用户信息
 */
public interface UserControllerApi {
    @ApiOperation("根据id查询app端用户信息")
    User findUserById(Integer id);

    @ApiOperation("用户注册")
    ResponseResult<?> UserRegister(User user);
}
