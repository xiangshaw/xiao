package plus.axz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.article.pojos.Collection;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 远程接口-收藏信息表
 */
@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {

}
