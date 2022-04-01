package plus.axz.api.wemedia;

import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.pojos.WmUser;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars
 */
public interface  WmUserControllerApi {
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
}
