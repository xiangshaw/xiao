package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.dtos.FollowBehaviorDto;
import plus.axz.model.behavior.pojos.FollowBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 关注行为service
 */
public interface FollowBehaviorService extends IService<FollowBehavior> {
    /**
     * 存储关注数据
     */
    ResponseResult<?> saveFollowBehavior(FollowBehaviorDto dto);
}
