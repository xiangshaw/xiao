package plus.axz.behavior.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.behavior.pojos.UnlikesBehavior;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 不喜欢行为
 */
@Mapper
public interface UnlikesBehaviorMapper extends BaseMapper<UnlikesBehavior> {
}
