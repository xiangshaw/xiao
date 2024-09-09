package plus.axz.behavior.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.behavior.pojos.ReadBehavior;

/**
 * @author xiaoxiang
 * description 阅读行为表mapper
 */
@Mapper
public interface ReadBehaviorMapper extends BaseMapper<ReadBehavior> {
}
