package plus.axz.model.comment.pojos;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 评论点赞信息
 */
@Data
@Document("comment_like")
public class CommentLike {
    /**
     * id
     */
    private String id;

    /**
     * 用户ID
     */
    private Integer authorId;

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}
