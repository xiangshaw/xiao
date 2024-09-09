package plus.axz.model.comment.dtos;

import lombok.Data;

import java.util.Date;

/**
 * @author xiaoxiang
 * description 加载评论回复列表参数
 */
@Data
public class CommentRepayDto {
    /**
     * 评论id
     */
    private String commentId;

    /**
     * 最小时间
     */
    private Date minDate;
}
