package plus.axz.model.comment.dtos;

import lombok.Data;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 评论点赞
 */
@Data
public class CommentLikeDto {
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
