package plus.axz.comment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import plus.axz.comment.feign.UserFeign;
import plus.axz.comment.service.CommentRepayService;
import plus.axz.model.comment.dtos.CommentRepayDto;
import plus.axz.model.comment.dtos.CommentRepayLikeDto;
import plus.axz.model.comment.dtos.CommentRepaySaveDto;
import plus.axz.model.comment.pojos.Comment;
import plus.axz.model.comment.pojos.CommentRepay;
import plus.axz.model.comment.pojos.CommentRepayLike;
import plus.axz.model.comment.vo.CommentRepayVo;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaoxiang
 * description 评论回复服务实现类
 */
@RequiredArgsConstructor
@Service
@Log4j2
public class CommentRepayServiceImpl implements CommentRepayService {

    private final MongoTemplate mongoTemplate;

    //加载评论回复列表
    @Override
    public ResponseResult<?> loadCommentRepay(CommentRepayDto dto) {
        //1.检查参数
        if (dto.getCommentId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }

        int size = 10;

        //2.根据文章id过滤查询,设置分页和排序
        Query query = Query.query(Criteria
                .where("commentId")
                .is(dto.getCommentId())
                .and("createdTime")
                .lt(dto.getMinDate()));
        query.limit(size).with(Sort.by(Sort.Direction.DESC, "createdTime"));
        List<CommentRepay> commentRepays = mongoTemplate.find(query, CommentRepay.class);

        //3.用户未登录，直接返回数据
        User user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.okResult(commentRepays);
        }

        //4.用户已登录，需要检索当前用户点赞了哪些评论

        //4.1查询点赞列表  userid和评论id
        List<String> idList = commentRepays.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<CommentRepayLike> commentRepayLikes = mongoTemplate.find(Query.query(Criteria
                .where("authorId")
                .is(user.getId())
                .and("commentRepayId")
                .in(idList)), CommentRepayLike.class);

        //4.2 判断当前评论哪些被点赞了

        List<CommentRepayVo> commentVoList = new ArrayList<>();

        if (commentRepayLikes != null) {
            commentRepays.stream().forEach(commentRepay -> {
                CommentRepayVo vo = new CommentRepayVo();
                BeanUtils.copyProperties(commentRepay, vo);
                for (CommentRepayLike commentLike : commentRepayLikes) {
                    if (commentRepay.getId().equals(commentLike.getCommentRepayId())) {
                        vo.setOperation((short) 0);
                        break;
                    }
                }
                commentVoList.add(vo);
            });

            return ResponseResult.okResult(commentVoList);

        } else {
            return ResponseResult.okResult(commentRepays);
        }
    }

    private final UserFeign userFeign;

    //保存回复内容
    @Override
    public ResponseResult<?> saveCommentRepay(CommentRepaySaveDto dto) {
        //1.检查参数
        if (dto.getCommentId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE);
        }
        if (dto.getContent() != null && dto.getContent().length() > 140) {
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE, "评论内容不能超过140字");
        }

        //2.判断是否登录
        User user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(ResultEnum.NEED_LOGIN);
        }

        //3.安全过滤,自行实现

        //4.保存评论
        User feignUserById = userFeign.findUserById(user.getId());
        if (feignUserById == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE, "当前登录信息有误");
        }
        CommentRepay commentRepay = new CommentRepay();
        commentRepay.setCommentId(dto.getCommentId());
        commentRepay.setAuthorId(feignUserById.getId());
        commentRepay.setAuthorName(feignUserById.getName());
        commentRepay.setContent(dto.getContent());
        commentRepay.setLikes(0);
        commentRepay.setUpdatedTime(new Date());
        commentRepay.setCreatedTime(new Date());

        mongoTemplate.insert(commentRepay);

        //更新评论的回复数量
        Comment comment = mongoTemplate.findById(dto.getCommentId(), Comment.class);
        comment.setReply(comment.getReply() + 1);
        mongoTemplate.save(comment);


        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    //点赞回复内容
    @Override
    public ResponseResult<?> saveCommentRepayLike(CommentRepayLikeDto dto) {
        //1.检查参数
        if (dto.getCommentRepayId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }

        //2.判断是否登录
        User user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(ResultEnum.NEED_LOGIN);
        }

        CommentRepay commentRepay = mongoTemplate.findById(dto.getCommentRepayId(), CommentRepay.class);
        if (commentRepay == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "当前评论没找到");
        }

        //3.点赞的操作
        if (dto.getOperation() == 0) {
            //更新评论的点赞数量
            commentRepay.setLikes(commentRepay.getLikes() + 1);
            mongoTemplate.save(commentRepay);

            //新增评论点赞的数据
            CommentRepayLike apCommentRepayLike = new CommentRepayLike();
            apCommentRepayLike.setAuthorId(user.getId());
            apCommentRepayLike.setCommentRepayId(commentRepay.getId());
            mongoTemplate.save(apCommentRepayLike);

        } else {
            //4.取消点赞的操作
            //更新评论的点赞数量
            commentRepay.setLikes(commentRepay.getLikes() <= 0 ? 0 : commentRepay.getLikes() - 1);
            mongoTemplate.save(commentRepay);

            //删除评论点赞的数据
            mongoTemplate.remove(Query.query(Criteria.where("authorId").is(user.getId())
                    .and("commentRepayId").is(commentRepay.getId())), CommentRepayLike.class);

        }

        //5.结果封装返回 --> 评论的点赞数量
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("likes", commentRepay.getLikes());
        return ResponseResult.okResult(resultMap);
    }
}
