package plus.axz.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.admin.pojos.News;

/**
 * @author xiaoxiang
 * description 新闻mapper
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {
}
