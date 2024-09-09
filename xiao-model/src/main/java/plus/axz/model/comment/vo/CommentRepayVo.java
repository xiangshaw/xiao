package plus.axz.model.comment.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.axz.model.comment.pojos.CommentRepay;

/**
 * @author xiaoxiang
 * description 加载评论回复列表数据封装类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentRepayVo extends CommentRepay {
    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}
