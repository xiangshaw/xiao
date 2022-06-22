package plus.axz.model.comment.vo;

import lombok.Data;
import plus.axz.model.comment.pojos.Comment;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 登录后封装数据vo
 */
@Data
public class CommentVo extends Comment {
    /**
     * 0 点赞
     * 1 未点赞
     */
    private Short operation;
}
