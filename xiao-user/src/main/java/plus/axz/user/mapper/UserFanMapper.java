package plus.axz.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.user.pojos.UserFan;

/**
 * @author xiaoxiang
 * description APP用户粉丝信息表 Mapper 接口
 */
@Mapper
public interface UserFanMapper extends BaseMapper<UserFan> {
}
