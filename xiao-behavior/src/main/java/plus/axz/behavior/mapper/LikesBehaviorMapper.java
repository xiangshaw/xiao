package plus.axz.behavior.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.behavior.pojos.LikesBehavior;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 点赞行为表mapper接口
 */
@Mapper
public interface LikesBehaviorMapper extends BaseMapper<LikesBehavior> {
}
