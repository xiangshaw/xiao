package plus.axz.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.search.pojos.AssociateWords;

/**
 * @author xiaoxiang
 * description 联想词的mapper接口
 */
@Mapper
public interface AssociateWordsMapper extends BaseMapper<AssociateWords> {
}
