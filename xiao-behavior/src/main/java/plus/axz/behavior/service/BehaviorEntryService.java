package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.pojos.BehaviorEntry;

/**
 * @author xiaoxiang
 * description 行为实体业务层
 */
public interface BehaviorEntryService extends IService<BehaviorEntry> {
    /**
     * 根据用户或设备查询行为实体
     */
    BehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer equipmentId);
}
