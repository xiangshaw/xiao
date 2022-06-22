package plus.axz.comment.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2022年06月22日
 * @particulars
 */
@RestController
@RequestMapping("/api/v1/comment_repay")
public class CommentRepayController implements CommentRepayControllerApi {
    @Autowired
    private CommentRepayService commentRepayService;

    @PostMapping("/load")
    @Override
    public ResponseResult loadCommentRepay(@RequestBody CommentRepayDto dto) {
        return commentRepayService.loadCommentRepay(dto);
    }

    @PostMapping("/save")
    @Override
    public ResponseResult saveCommentRepay(@RequestBody CommentRepaySaveDto dto) {
        return commentRepayService.saveCommentRepay(dto);
    }

    @PostMapping("/like")
    @Override
    public ResponseResult saveCommentRepayLike(@RequestBody CommentRepayLikeDto dto) {
        return commentRepayService.saveCommentRepayLike(dto);
    }
}
