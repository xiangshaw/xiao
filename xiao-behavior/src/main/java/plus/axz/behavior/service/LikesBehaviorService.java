package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.dtos.LikesBehaviorDto;
import plus.axz.model.behavior.pojos.LikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 点赞行为 服务接口
 */
public interface LikesBehaviorService extends IService<LikesBehavior> {
    public ResponseResult like(LikesBehaviorDto dto);
}
