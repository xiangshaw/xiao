package plus.axz.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.axz.model.user.pojos.User;
import plus.axz.user.mapper.UserMapper;
import plus.axz.user.service.UserService;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 根据id查询app用户信息
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
