package plus.axz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.pojos.User;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 根据id查询app用户信息
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @author xiaoxiang
     * @date 2022/8/9
     * @param user
     * @return plus.axz.model.user.pojos.User
     */
    ResponseResult UserRegister(User user);
}
