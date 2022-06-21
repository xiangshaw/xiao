package plus.axz.behavior.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.article.pojos.Collection;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 收藏行为
 */
@Mapper
public interface CollectionBehaviorMapper extends BaseMapper<Collection> {
}
