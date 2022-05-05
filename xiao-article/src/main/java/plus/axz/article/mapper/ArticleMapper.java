package plus.axz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.article.pojos.Article;

/**
 * @author xiaoxiang
 * @date 2022年05月03日
 * @particulars 文章信息
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
