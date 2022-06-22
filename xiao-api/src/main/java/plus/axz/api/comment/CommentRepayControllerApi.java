package plus.axz.api.comment;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.comment.dtos.CommentRepayDto;
import plus.axz.model.comment.dtos.CommentRepayLikeDto;
import plus.axz.model.comment.dtos.CommentRepaySaveDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 评论回复点赞
 * 1，用户点击回复，根据当前评论id查询对应的所有回复进行展示
 * 2，用户针对于当前的评论进行了回复，需要保存数据，同时需要更新当前评论的回复数
 * 3，可以对回复列表进行点赞操作
 */
public interface CommentRepayControllerApi {
    @ApiOperation("加载评论回复列表")
    public ResponseResult loadCommentRepay(CommentRepayDto dto);

    @ApiOperation("保存回复内容")
    public ResponseResult saveCommentRepay(CommentRepaySaveDto dto);

    @ApiOperation("点赞回复内容")
    public ResponseResult saveCommentRepayLike(CommentRepayLikeDto dto);
}
