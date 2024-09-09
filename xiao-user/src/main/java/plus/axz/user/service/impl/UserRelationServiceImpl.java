package plus.axz.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.axz.common.constants.message.FollowBehaviorConstants;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.behavior.dtos.FollowBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.dtos.UserRelationDto;
import plus.axz.model.user.pojos.User;
import plus.axz.model.user.pojos.UserFan;
import plus.axz.model.user.pojos.UserFollow;
import plus.axz.user.feign.ArticleFeign;
import plus.axz.user.mapper.UserFanMapper;
import plus.axz.user.mapper.UserFollowMapper;
import plus.axz.user.mapper.UserMapper;
import plus.axz.user.service.UserRelationService;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;

/**
 * @author xiaoxiang
 * description
 */
@RequiredArgsConstructor
@Service
@Log4j2
@Transactional
public class UserRelationServiceImpl implements UserRelationService {

    private final ArticleFeign articleFeign;

    /* @Override
 public ResponseResult<?> follow(UserRelationDto dto) {
     //1.参数检查
     if (dto.getOperation() == null || dto.getOperation() < 0 || dto.getOperation() > 1) {
         return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
     }
     if (dto.getAuthorId() == null) {
         return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "作者信息不能为空");
     }

     //2.获取作者
     Integer followId = null;
     ApAuthor apAuthor = articleFeign.findById(dto.getAuthorId());
     if (apAuthor != null) {
         followId = apAuthor.getUserId();
     }
     if (followId == null) {
         return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "关注人不存在");
     } else {

         ApUser apUser = AppThreadLocalUtils.getUser();*//*先拿到用户*//*
            if (apUser != null) {

                if (dto.getOperation() == 0) {
                    //3.如果当前操作是0 创建数据（app用户关注信息和app的用户粉丝信息）
                    return followByUserId(apUser, followId, dto.getArticleId());
                } else {
                    //4.如果当前操作是1 删除数据（app用户关注信息和app的用户粉丝信息）
                    return followCancelByUserId(apUser, followId);
                }
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
        }*/
    @Override
    public ResponseResult<?> follow(UserRelationDto dto) {
        // 1.参数检查
        if (dto.getOperation() == null || dto.getOperation() < 0 || dto.getOperation() > 1) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        if (dto.getAuthorId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE, "作者信息不能为空");
        }
        // 2.获取作者
        Integer followId = null;
        Author author = articleFeign.findById(dto.getAuthorId());
        if (author != null) {
            followId = author.getUserId();
        }
        if (followId == null) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST, "关注人不存在");
        }
        User user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(ResultEnum.NEED_LOGIN);
        }
        if (dto.getOperation() == 0) {
            // 3.如果当前操作是0 创建数据（app用户关注信息和app的用户粉丝信息）
            return followByUserId(user, followId, dto.getArticleId());
        }
        if (dto.getOperation() == 1) {
            // 4.如果当前操作是1 删除数据（app用户关注信息和app的用户粉丝信息）
            return followCancelByUserId(user, followId);
        }
        return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
    }

    // =================================================================================

    private final UserMapper userMapper;

    private final UserFollowMapper userFollowMapper;

    private final UserFanMapper userFanMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 关注的处理逻辑
     */
    private ResponseResult<?> followByUserId(User user, Integer followId, Long articleId) {
        // 1.判断当前关注人是否存在
        User followUser = userMapper.selectById(followId);
        if (followUser == null) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST, "关注用户不存在");
        }
        UserFollow userFollow = userFollowMapper.selectOne(Wrappers.<UserFollow>lambdaQuery()
                .eq(UserFollow::getUserId, user.getId())
                .eq(UserFollow::getFollowId, followId));
        if (userFollow == null) {
            // 保存数据  user_follow   查user_fan
            UserFan userFan = userFanMapper.selectOne(Wrappers.<UserFan>lambdaQuery()
                    .eq(UserFan::getUserId, followId)
                    .eq(UserFan::getFansId, user.getId()));
            // 保存app用户粉丝信息
            if (userFan == null) {
                userFan = new UserFan();
                userFan.setUserId(followId);
                userFan.setFansId(user.getId().longValue());
                // 当前登陆人名称
                userFan.setFansName(userMapper.selectById(user.getId().longValue()).getName());
                userFan.setLevel((short) 0);
                // 动态可见
                userFan.setIsDisplay(true);
                userFan.setIsShieldLetter(false);
                userFan.setIsShieldComment(false);
                userFan.setCreatedTime(new Date());
                userFanMapper.insert(userFan);
            }
            // 保存app用户关注信息
            userFollow = new UserFollow();
            userFollow.setUserId(user.getId());
            userFollow.setFollowId(followId);
            userFollow.setFollowName(followUser.getName());
            userFollow.setCreatedTime(new Date());
            // 动态通知
            userFollow.setIsNotice(true);
            userFollow.setLevel((short) 1);
            userFollowMapper.insert(userFollow);
            //记录关注文章的行为
            FollowBehaviorDto behaviorDto = new FollowBehaviorDto();
            behaviorDto.setFollowId(followId);
            behaviorDto.setArticleId(articleId);
            behaviorDto.setUserId(user.getId());
            //异步发送消息，保存关注行为
            kafkaTemplate.send(FollowBehaviorConstants.FOLLOW_BEHAVIOR_TOPIC, JSON.toJSONString(behaviorDto));
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        } else {
            // 已关注
            return ResponseResult.errorResult(ResultEnum.DATA_EXIST, "已关注");
        }
    }

    /**
     * 取消关注逻辑
     */
    private ResponseResult<?> followCancelByUserId(User user, Integer followId) {
        // 1.先查再删
        UserFollow userFollow = userFollowMapper.selectOne(Wrappers.<UserFollow>lambdaQuery()
                .eq(UserFollow::getUserId, user.getId())
                .eq(UserFollow::getFollowId, followId));
        if (userFollow == null) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST, "未关注");
        } else {
            UserFan userFan = userFanMapper.selectOne(Wrappers.<UserFan>lambdaQuery()
                    .eq(UserFan::getUserId, followId)
                    .eq(UserFan::getFansId, user.getId()));
            // 删除用户粉丝信息
            if (user != null) {
                // 查多少删多少
                userFanMapper.delete(Wrappers.<UserFan>lambdaQuery()
                        .eq(UserFan::getUserId, followId)
                        .eq(UserFan::getUserId, user.getId()));
            }
            // 删除用户关注信息
            userFollowMapper.delete(Wrappers.<UserFollow>lambdaQuery()
                    .eq(UserFollow::getUserId, user.getId())
                    .eq(UserFollow::getFollowId, followId));
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        }
    }
}
