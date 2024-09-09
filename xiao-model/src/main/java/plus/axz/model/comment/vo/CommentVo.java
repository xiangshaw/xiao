package plus.axz.model.comment.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.axz.model.comment.pojos.Comment;

/**
 * @author xiaoxiang
 * description 登录后封装数据vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVo extends Comment {
    /**
     * 0 点赞
     * 1 未点赞
     */
    private Short operation;
}
