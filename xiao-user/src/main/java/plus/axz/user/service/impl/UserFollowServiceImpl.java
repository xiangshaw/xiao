package plus.axz.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.axz.model.user.pojos.UserFollow;
import plus.axz.user.mapper.UserFollowMapper;
import plus.axz.user.service.UserFollowService;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 远程接口- APP用户关注信息表 服务实现类
 */
@Slf4j
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

}
