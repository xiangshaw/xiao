package plus.axz.behavior.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.behavior.pojos.BehaviorEntry;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars 在行为微服务中查询行为实体
 */
@Mapper
public interface BehaviorEntryMapper extends BaseMapper<BehaviorEntry> {
}
