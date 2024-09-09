package plus.axz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.article.pojos.Author;

/**
 * @author xiaoxiang
 * description
 */
@Mapper
public interface AuthorMapper extends BaseMapper<Author> {
}
