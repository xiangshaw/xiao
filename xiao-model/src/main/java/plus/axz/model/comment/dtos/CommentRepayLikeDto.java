package plus.axz.model.comment.dtos;

import lombok.Data;

/**
 * @author xiaoxiang
 * description 点赞回复内容参数
 */
@Data
public class CommentRepayLikeDto {
    /**
     * 回复id
     */
    private String commentRepayId;

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}
