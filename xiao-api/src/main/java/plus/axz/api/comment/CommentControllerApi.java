package plus.axz.api.comment;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.comment.dtos.CommentLikeDto;
import plus.axz.model.comment.dtos.CommentSaveDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 评论
 */
public interface CommentControllerApi {
    @ApiOperation("保存评论")
    public ResponseResult saveComment(CommentSaveDto dto);
    @ApiOperation("点赞或取消点赞")
    public ResponseResult like(CommentLikeDto dto);
}
