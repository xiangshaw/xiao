package plus.axz.api.comment;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.comment.dtos.CommentDto;
import plus.axz.model.comment.dtos.CommentLikeDto;
import plus.axz.model.comment.dtos.CommentSaveDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 评论
 */
public interface CommentControllerApi {
    @ApiOperation("保存评论")
    ResponseResult<?> saveComment(CommentSaveDto dto);

    @ApiOperation("点赞或取消点赞")
    ResponseResult<?> like(CommentLikeDto dto);

    @ApiOperation("查询评论")
    ResponseResult<?> findByArticleId(CommentDto dto);
}
