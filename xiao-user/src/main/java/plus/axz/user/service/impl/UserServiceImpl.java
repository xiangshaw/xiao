package plus.axz.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.user.mapper.UserMapper;
import plus.axz.user.service.UserService;
import plus.axz.utils.common.MD5Utils;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 根据id查询app用户信息
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 用户注册
    @Override
    public ResponseResult UserRegister(User user) {
        // 1.检查参数
        if (user == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.查数据库
        List<User> list = list(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()));
        if (list != null && list.size() == 1){
            return ResponseResult.errorResult(ResultEnum.DATA_EXIST,"用户名已存在");
        }
        // 3.保存数据
        user.setIdentityAuthentication(false);
        user.setStatus(false);
        user.setFlag((short) 0);
        user.setCreatedTime(new Date());
        user.setPassword(MD5Utils.encodeWithSalt(user.getPassword(),user.getSalt()));
        save(user);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
