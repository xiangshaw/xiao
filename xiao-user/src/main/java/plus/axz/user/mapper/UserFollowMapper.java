package plus.axz.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.user.pojos.UserFollow;

/**
 * @author xiaoxiang
 * description APP用户关注信息表 Mapper 接口
 */
@Mapper
public interface UserFollowMapper extends BaseMapper<UserFollow> {
}
