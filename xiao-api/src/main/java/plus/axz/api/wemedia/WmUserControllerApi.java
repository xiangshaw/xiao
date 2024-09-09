package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.pojos.WmUser;

/**
 * @author xiaoxiang
 * description 自媒体用户接口
 */
public interface WmUserControllerApi {
    /**
     * 保存自媒体用户
     */
    public ResponseResult save(WmUser wmUser);

    /**
     * 按照名称查询用户
     */
    public WmUser findByName(String name);

    /**
     * 根据id查询用户
     * admin远程调用
     */
    @ApiOperation("根据id查询用户")
    WmUser findWmUserById(Long id);
}
