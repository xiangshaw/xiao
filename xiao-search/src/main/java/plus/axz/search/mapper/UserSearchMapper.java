package plus.axz.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.search.pojos.UserSearch;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars APP用户搜索信息表 Mapper 接口
 */
@Mapper
public interface UserSearchMapper extends BaseMapper<UserSearch> {
}
