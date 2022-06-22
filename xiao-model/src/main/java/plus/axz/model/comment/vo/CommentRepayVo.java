package plus.axz.model.comment.vo;

import lombok.Data;
import plus.axz.model.comment.pojos.CommentRepay;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 加载评论回复列表数据封装类
 */
@Data
public class CommentRepayVo extends CommentRepay {
    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}
