package plus.axz.comment.service;

import plus.axz.model.comment.dtos.CommentLikeDto;
import plus.axz.model.comment.dtos.CommentSaveDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
public interface CommentService {
    /**
     * 保存评论
     */
    public ResponseResult saveComment(CommentSaveDto dto);
    /**
     * 点赞或取消点赞
     */
    public ResponseResult like(CommentLikeDto dto);
}
