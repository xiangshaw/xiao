package plus.axz.behavior.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import plus.axz.behavior.mapper.BehaviorEntryMapper;
import plus.axz.behavior.service.BehaviorEntryService;
import plus.axz.model.behavior.pojos.BehaviorEntry;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 根据用户、设备ID查询行为实体
 */
@Service
@Log4j2
public class BehaviorEntryServiceImpl extends ServiceImpl<BehaviorEntryMapper, BehaviorEntry> implements BehaviorEntryService {
    @Override
    public BehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer equipmentId) {
        // 1.根据用户查询行为实体、
        if (userId !=null){
            return getOne(Wrappers.<BehaviorEntry>lambdaQuery()
                    .eq(BehaviorEntry::getEntryId,userId)
                    .eq(BehaviorEntry::getType,1));
        }
        // 2.根据设备id查询行为实体
        if (userId == null && equipmentId != null && equipmentId !=0){
            return getOne(Wrappers.<BehaviorEntry>lambdaQuery()
                    .eq(BehaviorEntry::getEntryId,equipmentId)
                    .eq(BehaviorEntry::getType,0));
        }
        // 3.要是没有，直接返回null
        return null;
    }
}
