package plus.axz.behavior.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.axz.behavior.mapper.UnlikesBehaviorMapper;
import plus.axz.behavior.service.BehaviorEntryService;
import plus.axz.behavior.service.UnlikesBehaviorService;
import plus.axz.model.behavior.dtos.UnLikesBehaviorDto;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.behavior.pojos.UnlikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;

/**
 * @author xiaoxiang
 * description 不喜欢行为实现类
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UnlikesBehaviorServiceImpl extends ServiceImpl<UnlikesBehaviorMapper, UnlikesBehavior> implements UnlikesBehaviorService {

    private final BehaviorEntryService behaviorEntryService;

    @Override
    public ResponseResult<?> unlikeBehavior(UnLikesBehaviorDto dto) {
        // 1.检查参数
        if (dto == null || dto.getEquipmentId() == null || (dto.getType() < 0 && dto.getType() > 1)) {
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE);
        }
        // 2.查询行为实体
        User user = AppThreadLocalUtils.getUser();
        BehaviorEntry behaviorEntry = behaviorEntryService.findByUserIdOrEquipmentId(user.getId(), dto.getEquipmentId());
        if (behaviorEntry == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.保存或更新
        UnlikesBehavior unlikesBehavior = getOne(Wrappers.<UnlikesBehavior>lambdaQuery()
                .eq(UnlikesBehavior::getArticleId, dto.getArticleId())
                .eq(UnlikesBehavior::getEntryId, behaviorEntry.getId()));
        if (unlikesBehavior == null) {
            unlikesBehavior = new UnlikesBehavior();
            unlikesBehavior.setEntryId(behaviorEntry.getId());
            unlikesBehavior.setArticleId(dto.getArticleId());
            unlikesBehavior.setType(dto.getType());
            unlikesBehavior.setCreatedTime(new Date());
            save(unlikesBehavior);
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        } else {
            unlikesBehavior.setType(dto.getType());
            updateById(unlikesBehavior);
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        }
    }
}
