package plus.axz.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.admin.pojos.Sensitive;

/**
 * 敏感词mapper接口
 * @author xiaoxiang
 * @date 2022/3/23
 */
@Mapper
public interface SensitiveMapper extends BaseMapper<Sensitive> {
}
