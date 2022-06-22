package plus.axz.comment.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import plus.axz.comment.feign.SensitiveFeign;
import plus.axz.comment.feign.UserFeign;
import plus.axz.comment.service.CommentService;
import plus.axz.common.aliyun.GreeTextScan;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.comment.dtos.CommentSaveDto;
import plus.axz.model.comment.pojos.Comment;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.common.SensitiveWordUtil;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
        if (!sensitiveScanBoolean){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"评论包含敏感词");
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
        apComment.setCreatedTime(new Date());
        apComment.setUpdatedTime(new Date());
        apComment.setImage(user1.getImage());
        apComment.setLikes(0);
        apComment.setReply(0);
        apComment.setType((short) 0);
        apComment.setFlag((short) 0);
        mongoTemplate.insert(apComment);

        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Autowired
    private SensitiveFeign sensitiveFeign;
    // 评论审核
    private boolean handleSensitive(String content) {
        boolean flag =true;
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


}
