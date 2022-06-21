package plus.axz.behavior.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.behavior.mapper.FollowBehaviorMapper;
import plus.axz.behavior.service.BehaviorEntryService;
import plus.axz.behavior.service.FollowBehaviorService;
import plus.axz.model.behavior.dtos.FollowBehaviorDto;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.behavior.pojos.FollowBehavior;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars APP关注行为表 服务实现类
 */
@Slf4j
@Service
public class FollowBehaviorServiceImpl extends ServiceImpl<FollowBehaviorMapper, FollowBehavior> implements FollowBehaviorService {

    @Autowired
    private BehaviorEntryService behaviorEntryService;
    @Override
    public ResponseResult saveFollowBehavior(FollowBehaviorDto dto) {
        // 1.查询行为实体 ---没有设备id直接传null----该方法返回的是设备，就是当前的行为
        BehaviorEntry behaviorEntry = behaviorEntryService.findByUserIdOrEquipmentId(dto.getUserId(), null);
        // 2.如果行为数据没有查出来
        if (behaviorEntry == null){
            log.error("行为实体为空");
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.保存关注行为
        FollowBehavior followBehavior = new FollowBehavior();
        followBehavior.setEntryId(behaviorEntry.getId());
        followBehavior.setCreatedTime(new Date());
        followBehavior.setArticleId(dto.getArticleId());
        followBehavior.setFollowId(dto.getFollowId());
        return ResponseResult.okResult(save(followBehavior));
    }
}
