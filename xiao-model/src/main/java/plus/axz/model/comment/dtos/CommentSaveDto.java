package plus.axz.model.comment.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 发表评论
 */
@Data
public class CommentSaveDto {
    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;
}
