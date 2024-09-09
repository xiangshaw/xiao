package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.admin.dtos.AdUserDto;
import plus.axz.model.admin.pojos.AdUser;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 登录服务
 */
public interface UserLoginService extends IService<AdUser> {
    /**
     * 登录功能
     */
    ResponseResult<?> login(AdUserDto dto);
}
