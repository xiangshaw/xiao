package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.dtos.LikesBehaviorDto;
import plus.axz.model.behavior.pojos.LikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 点赞行为 服务
 */
public interface LikesBehaviorService extends IService<LikesBehavior> {
    ResponseResult<?> like(LikesBehaviorDto dto);
}
