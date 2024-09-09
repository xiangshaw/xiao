package plus.axz.comment.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.comment.CommentControllerApi;
import plus.axz.comment.service.CommentService;
import plus.axz.model.comment.dtos.CommentDto;
import plus.axz.model.comment.dtos.CommentLikeDto;
import plus.axz.model.comment.dtos.CommentSaveDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 评论
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController implements CommentControllerApi {

    private final CommentService commentService;

    // 保存评论
    @PostMapping("/save")
    @Override
    public ResponseResult<?> saveComment(@RequestBody CommentSaveDto dto) {
        return commentService.saveComment(dto);
    }

    // 点赞或取消点赞
    @PostMapping("/like")
    @Override
    public ResponseResult<?> like(@RequestBody CommentLikeDto dto) {
        return commentService.like(dto);
    }

    // 查询文章评论列表
    @PostMapping("/load")
    @Override
    public ResponseResult<?> findByArticleId(@RequestBody CommentDto dto) {
        return commentService.findByArticleId(dto);
    }
}
