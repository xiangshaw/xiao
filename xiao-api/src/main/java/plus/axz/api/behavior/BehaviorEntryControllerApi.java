package plus.axz.api.behavior;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.behavior.pojos.BehaviorEntry;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars 行为微服务
 */
public interface BehaviorEntryControllerApi {
    @ApiOperation("根据用户或设备查询行为实体")
    public BehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer equipmentId);
}
