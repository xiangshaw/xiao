package plus.axz.model.article.vo;

import lombok.Data;
import plus.axz.model.article.pojos.Article;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars 分值
 */
@Data
public class HotArticleVo extends Article {
    private Integer score;
}
