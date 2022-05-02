package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.pojos.WmUser;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars
 */
public interface WmUserControllerApi {
    /**
     * 保存自媒体用户
     *
     * @param wmUser
     * @return
     */
    public ResponseResult save(WmUser wmUser);

    /**
     * 按照名称查询用户
     *
     * @param name
     * @return
     */
    public WmUser findByName(String name);

    /**
     * 根据id查询用户
     * admin远程调用
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询用户")
    WmUser findWmUserById(Long id);
}
