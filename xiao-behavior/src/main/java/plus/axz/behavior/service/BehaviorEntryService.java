package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.pojos.BehaviorEntry;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 行为实体业务层
 */
public interface BehaviorEntryService extends IService<BehaviorEntry> {
    /**
     * 根据用户或设备查询行为实体
     * @author xiaoxiang
     * @date 2022/6/21
     * @param userId
     * @param equipmentId
     * @return ApBehaviorEntry
     */
    public BehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer equipmentId);
}
