package plus.axz.comment.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import plus.axz.comment.feign.SensitiveFeign;
import plus.axz.comment.feign.UserFeign;
import plus.axz.comment.service.CommentService;
import plus.axz.common.aliyun.GreeTextScan;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.comment.dtos.CommentDto;
import plus.axz.model.comment.dtos.CommentLikeDto;
import plus.axz.model.comment.dtos.CommentSaveDto;
import plus.axz.model.comment.pojos.Comment;
import plus.axz.model.comment.pojos.CommentLike;
import plus.axz.model.comment.vo.CommentVo;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.common.SensitiveWordUtil;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
@Service
@Log4j2
public class CommentServiceImpl implements CommentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private GreeTextScan greeTextScan;

    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
        //1.检查参数
        if (dto.getArticleId() == null) {
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

        //3.安全过滤-调用阿里云检测文本是否合规
        boolean sensitiveScanBoolean = handleSensitive(dto.getContent());
        if (!sensitiveScanBoolean) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "评论包含敏感词");
        }

        //4.保存评论
        User user1 = userFeign.findUserById(user.getId());
        if (user1 == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE, "当前登录信息有误");
        }
        Comment apComment = new Comment();
        apComment.setAuthorId(user1.getId());
        apComment.setAuthorName(user1.getName());
        apComment.setContent(dto.getContent());/*评论内容*/
        apComment.setEntryId(dto.getArticleId());
        /*获取MongoDB的当前时间，时区UTC转GMT*/
        Calendar calle = getMongoDBTime();
        apComment.setCreatedTime(calle.getTime());
        apComment.setUpdatedTime(calle.getTime());
        apComment.setImage(user1.getImage());
        apComment.setLikes(0);
        apComment.setReply(0);
        apComment.setType((short) 0);
        apComment.setFlag((short) 0);
        mongoTemplate.insert(apComment);

        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    // 时间换算
    private Calendar getMongoDBTime() {
        Calendar calle = Calendar.getInstance();
        calle.setTime(new Date());
        calle.add(Calendar.HOUR_OF_DAY, +8);
        return calle;
    }

    @Autowired
    private SensitiveFeign sensitiveFeign;

    // 评论审核
    private boolean handleSensitive(String content) {
        boolean flag = true;
        // 返回所有敏感词
        List<Sensitive> findallSensitive = sensitiveFeign.findAllSensitive();
        List<String> list = findallSensitive.stream().map(Sensitive::getSensitives).distinct().collect(Collectors.toList());
        // 初始化敏感词
        SensitiveWordUtil.initMap(list);
        // 内容自管理敏感词过滤
        Map<String, Integer> resultMap = SensitiveWordUtil.matchWords(content);
        if (resultMap.size() > 0) {
            log.error("敏感词审核未通过，包含了敏感词:{}", resultMap);
            // 找到敏感词，审核不通过
            flag = false;
        }
        return flag;
    }

    // 点赞或取消点赞
    @Override
    public ResponseResult like(CommentLikeDto dto) {
        // 1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断登录
        User user = new User();
        if (user == null) {
            return ResponseResult.errorResult(ResultEnum.NEED_LOGIN);
        }
        Comment comment = mongoTemplate.findById(dto.getCommentId(), Comment.class);
        if (comment == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "当前评论未找到");
        }
        // 3.点赞操作
        if (dto.getOperation() == 0) {
            // 更新评论点赞数据
            comment.setLikes(comment.getLikes() + 1);
            // 更新数据-save既有保存也有更新效果
            mongoTemplate.save(comment);
            // 新增评论点赞数据
            CommentLike commentLike = new CommentLike();
            commentLike.setAuthorId(user.getId());
            commentLike.setCommentId(comment.getId());
            // 保存评论点赞数据
            mongoTemplate.save(commentLike);
        } else {
            // 4.取消点赞的操作
            // 更新评论点赞数据--小于0就设置为0，不小于0 才-1
            comment.setLikes(comment.getLikes() <= 0 ? 0 : comment.getLikes() - 1);
            mongoTemplate.save(comment);
            // 删除评论点赞数据--需加上指定集合
            mongoTemplate.remove(Query
                    .query(Criteria
                            .where("authorId")
                            .is(user.getId())
                            .and("commentId")
                            .is(comment.getId())), CommentLike.class);
        }
        // 5.结果封装返回 -》评论点赞数量
        HashMap<String, Object> map = new HashMap<>();
        map.put("likes", comment.getLikes());
        return ResponseResult.okResult(map);
    }

    // 查询文章评论列表
    @Override
    public ResponseResult findByArticleId(CommentDto dto) {
        // 1.检查参数
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 默认十条
        int size = 10;
        // 2.根据文章id过滤查询，设置分页和排序
        Query query = Query.query(Criteria.where("entryId")
                .is(dto.getArticleId())
                .and("createdTime")
                .lt(dto.getMinDate()));// 根据文章或动态ID进行检索
/*
         is  精确匹配
         模糊匹配 使用regex
        "$gte"---大于等于
        "$gt"----大于
        "$lt"----小于
        "$lte"----小于等于
        "$in"----在此范围
        "$nin"----不在此范围*/
        // 设置分页和排序  -- 根据创建时间倒叙排序
        query.limit(size).with(Sort.by(Sort.Direction.DESC, "createdTime"));
        // 分页之后的评论列表
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        for (Comment comment : comments) {
            Date date = setMongoDBDate(comment);
            comment.setUpdatedTime(date);
        }
        // 3.用户未登录，直接返回数据
        User user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.okResult(comments);
        }
        // 4.用户已登录，需要检索当前用户点赞了哪些评论
        // 4.1查询点赞列表 userId和评论id
        List<String> idList = comments.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<CommentLike> commentLikes = mongoTemplate.find(Query.query(Criteria
                .where("authorId")
                .is(user.getId())
                .and("commentId")
                .in(idList)), CommentLike.class);
        // 4.2检索判断当前哪些评论点赞了
        List<CommentVo> commentVoList = new ArrayList<>();
        if (commentLikes != null) {
            // 有被点赞
            comments.stream().forEach(comment -> {
                CommentVo vo = new CommentVo();
                // 拷贝工作，创建VO
                BeanUtils.copyProperties(comment, vo);
                for (CommentLike commentLike : commentLikes) {
                    if (comment.getId().equals(commentLike.getCommentId())) {
                        vo.setOperation((short) 0);
                        break;
                    } // 找评论哪些被点赞（标识0）
                }
                commentVoList.add(vo);
            });
            return ResponseResult.okResult(commentVoList);
        } else {
            // 未点赞
            return ResponseResult.okResult(comments);
        }
    }

    // 设置获取的时间差 -- 待优化
    private Date setMongoDBDate(Comment comment) {
        long time = comment.getUpdatedTime().getTime();
        long a = time - 28800000;
        Date date = new Date(a);
        return date;
    }
}
