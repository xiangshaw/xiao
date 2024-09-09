package plus.axz.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.admin.pojos.AdUser;

/**
 * @author xiaoxiang
 * description 管理员用户mapper
 */
@Mapper
public interface AdUserMapper extends BaseMapper<AdUser> {
}
