package plus.axz.behavior.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.behavior.pojos.FollowBehavior;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars APP关注行为表 Mapper 接口
 */
@Mapper
public interface FollowBehaviorMapper extends BaseMapper<FollowBehavior> {
}
