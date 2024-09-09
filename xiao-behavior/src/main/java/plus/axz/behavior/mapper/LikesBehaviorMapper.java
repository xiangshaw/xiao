package plus.axz.behavior.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.behavior.pojos.LikesBehavior;

/**
 * @author xiaoxiang
 * description 点赞行为表mapper接口
 */
@Mapper
public interface LikesBehaviorMapper extends BaseMapper<LikesBehavior> {
}
