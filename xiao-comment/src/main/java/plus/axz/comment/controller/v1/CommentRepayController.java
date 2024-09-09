package plus.axz.comment.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.comment.CommentRepayControllerApi;
import plus.axz.comment.service.CommentRepayService;
import plus.axz.model.comment.dtos.CommentRepayDto;
import plus.axz.model.comment.dtos.CommentRepayLikeDto;
import plus.axz.model.comment.dtos.CommentRepaySaveDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 评论回复
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment_repay")
public class CommentRepayController implements CommentRepayControllerApi {

    private final CommentRepayService commentRepayService;

    // 加载评论回复列表
    @PostMapping("/load")
    @Override
    public ResponseResult<?> loadCommentRepay(@RequestBody CommentRepayDto dto) {
        return commentRepayService.loadCommentRepay(dto);
    }

    // 保存回复内容
    @PostMapping("/save")
    @Override
    public ResponseResult<?> saveCommentRepay(@RequestBody CommentRepaySaveDto dto) {
        return commentRepayService.saveCommentRepay(dto);
    }

    // 点赞回复内容
    @PostMapping("/like")
    @Override
    public ResponseResult<?> saveCommentRepayLike(@RequestBody CommentRepayLikeDto dto) {
        return commentRepayService.saveCommentRepayLike(dto);
    }
}
