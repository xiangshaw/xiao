package plus.axz.behavior.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import plus.axz.behavior.mapper.ReadBehaviorMapper;
import plus.axz.behavior.service.BehaviorEntryService;
import plus.axz.behavior.service.ReadBehaviorService;
import plus.axz.model.behavior.dtos.ReadBehaviorDto;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.behavior.pojos.ReadBehavior;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 保存阅读行为
 */
@Service
@Log4j2
public class ReadBehaviorServiceImpl extends ServiceImpl<ReadBehaviorMapper, ReadBehavior> implements ReadBehaviorService {
    @Autowired
    private BehaviorEntryService behaviorEntryService;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public ResponseResult readBehavior(ReadBehaviorDto dto) {
        // 1.参数校验
        if (dto == null || dto.getArticleId() == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.查询行为实体
        User user = AppThreadLocalUtils.getUser();
        BehaviorEntry behaviorEntry = behaviorEntryService.findByUserIdOrEquipmentId(user.getId(), dto.getEquipmentId());
        // 如果行为实体未查到
        if (behaviorEntry == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.保存或更新阅读行为
        ReadBehavior readBehavior = getOne(Wrappers.<ReadBehavior>lambdaQuery()
                .eq(ReadBehavior::getArticleId, dto.getArticleId())
                .eq(ReadBehavior::getEntryId, behaviorEntry.getId()));
        // 判断阅读行为对象
        if (readBehavior == null){
            readBehavior = new ReadBehavior();
            readBehavior.setCount(dto.getCount()); // 默认传1
            readBehavior.setArticleId(dto.getArticleId());
            readBehavior.setPercentage(dto.getPercentage());
            readBehavior.setEntryId(behaviorEntry.getId());
            readBehavior.setReadDuration(dto.getReadDuration());
            readBehavior.setLoadDuration(dto.getLoadDuration());
            readBehavior.setCreatedTime(new Date());
            save(readBehavior);
        }else {
            // 有值更新值
            readBehavior.setUpdatedTime(new Date());
            // 阅读次数+1
            readBehavior.setCount((short) (readBehavior.getCount()+1));
            // 阅读时间
            readBehavior.setReadDuration(dto.getReadDuration());
            // 文章加载时间
            readBehavior.setLoadDuration(dto.getLoadDuration());
            updateById(readBehavior);
        }
        // 发送消息 TODO
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
