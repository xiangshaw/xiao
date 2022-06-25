package plus.axz.model.article.mess;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars 聚合
 */
@Data
public class ArticleVisitStreamMess {
    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;
    /**
     * 阅读
     */
    private long view;
    /**
     * 收藏
     */
    private long collect;
    /**
     * 评论
     */
    private long comment;
    /**
     * 点赞
     */
    private long like;
}
