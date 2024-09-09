package plus.axz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.article.pojos.ArticleContent;

/**
 * @author xiaoxiang
 * description 文章内容
 */
@Mapper
public interface ArticleContentMapper extends BaseMapper<ArticleContent> {
}
