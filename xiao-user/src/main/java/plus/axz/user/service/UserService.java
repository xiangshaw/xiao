package plus.axz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.pojos.User;

/**
 * @author xiaoxiang
 * description 根据id查询app用户信息
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     */
    ResponseResult<?> userRegister(User user);
}
