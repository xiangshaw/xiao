package plus.axz.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmUserDto;
import plus.axz.model.wemedia.pojos.WmUser;

/**
 * @author xiaoxiang
 * description
 */
public interface WmUserService extends IService<WmUser> {
    /**
     * 登录
     */
    ResponseResult<?> login(WmUserDto dto);
}
