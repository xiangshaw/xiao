package plus.axz.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.wemedia.pojos.WmNews;

/**
 * @author xiaoxiang
 * @date 2022年04月03日
 * @particulars 自媒体文章
 */
@Mapper
public interface WmNewsMapper extends BaseMapper<WmNews> {
}
