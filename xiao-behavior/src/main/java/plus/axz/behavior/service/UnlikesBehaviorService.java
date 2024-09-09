package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.dtos.UnLikesBehaviorDto;
import plus.axz.model.behavior.pojos.UnlikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 不喜欢行为
 */
public interface UnlikesBehaviorService extends IService<UnlikesBehavior> {
    ResponseResult<?> unlikeBehavior(UnLikesBehaviorDto dto);
}
