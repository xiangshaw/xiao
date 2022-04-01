package plus.axz.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmUserDto;
import plus.axz.model.wemedia.pojos.WmUser;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars
 */
public interface WmUserService extends IService<WmUser> {
    /**
     * 登录
     * @author xiaoxiang
     * @date 2022/4/1
     * @param dto
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult login(WmUserDto dto);
}
