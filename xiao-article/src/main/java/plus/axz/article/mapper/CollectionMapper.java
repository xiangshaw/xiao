package plus.axz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.article.pojos.Collection;

/**
 * @author xiaoxiang
 * description 远程接口-收藏信息表
 */
@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {

}
