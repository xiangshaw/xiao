package plus.axz.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.axz.model.wemedia.WmUser;
import plus.axz.wemedia.mapper.WmUserMapper;
import plus.axz.wemedia.service.WmUserService;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {
}
