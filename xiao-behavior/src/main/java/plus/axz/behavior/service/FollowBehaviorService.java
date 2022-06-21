package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.dtos.FollowBehaviorDto;
import plus.axz.model.behavior.pojos.FollowBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
public interface FollowBehaviorService extends IService<FollowBehavior> {
    /**
     * 存储关注数据
     * @author xiaoxiang
     * @date 2022/6/21
     * @param dto
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult saveFollowBehavior(FollowBehaviorDto dto);
}
