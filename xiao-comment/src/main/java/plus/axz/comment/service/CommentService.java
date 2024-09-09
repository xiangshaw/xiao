package plus.axz.comment.service;

import plus.axz.model.comment.dtos.CommentDto;
import plus.axz.model.comment.dtos.CommentLikeDto;
import plus.axz.model.comment.dtos.CommentSaveDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 评论服务
 */
public interface CommentService {
    /**
     * 保存评论
     */
    ResponseResult<?> saveComment(CommentSaveDto dto);

    /**
     * 点赞或取消点赞
     */
    ResponseResult<?> like(CommentLikeDto dto);

    /**
     * 查询文章评论列表
     */
    ResponseResult<?> findByArticleId(CommentDto dto);
}
