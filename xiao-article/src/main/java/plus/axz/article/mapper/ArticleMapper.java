package plus.axz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.model.article.dtos.ArticleHomeDto;
import plus.axz.model.article.pojos.Article;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年05月03日
 * @particulars 文章信息
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /*type 加载方式，见xml文件*/
    public List<Article> loadArticleList(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);
}
