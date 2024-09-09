package plus.axz.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.user.pojos.User;

/**
 * @author xiaoxiang
 * description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
