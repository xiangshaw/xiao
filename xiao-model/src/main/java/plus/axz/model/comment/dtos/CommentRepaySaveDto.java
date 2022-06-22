package plus.axz.model.comment.dtos;

import lombok.Data;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 保存回复内容参数
 */
@Data
public class CommentRepaySaveDto {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 回复内容
     */
    private String content;
}
