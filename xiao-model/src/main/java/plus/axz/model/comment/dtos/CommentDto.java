package plus.axz.model.comment.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

import java.util.Date;

/**
 * @author xiaoxiang
 * description 查询评论列表
 */
@Data
public class CommentDto {
    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    // 最小时间
    private Date minDate;

    //是否是首页
    private Short index;

}
