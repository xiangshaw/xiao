package plus.axz.model.comment.pojos;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author xiaoxiang
 * description APP评论回复信息点赞信息
 */
@Data
@Document("comment_repay_like")
public class CommentRepayLike {

    /**
     * id
     */
    private String id;

    /**
     * 用户ID
     */
    private Integer authorId;

    /**
     * 评论回复id
     */
    private String commentRepayId;

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}
