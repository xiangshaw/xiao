package plus.axz.model.article.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.axz.model.article.pojos.Article;

/**
 * @author xiaoxiang
 * description 分值
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HotArticleVo extends Article {
    private Integer score;
}
