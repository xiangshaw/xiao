package plus.axz.behavior.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import plus.axz.behavior.mapper.LikesBehaviorMapper;
import plus.axz.behavior.service.BehaviorEntryService;
import plus.axz.behavior.service.LikesBehaviorService;
import plus.axz.common.constants.message.HotArticleConstants;
import plus.axz.model.article.mess.UpdateArticleMess;
import plus.axz.model.behavior.dtos.LikesBehaviorDto;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.behavior.pojos.LikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
@Service
public class LikesBehaviorServiceImpl extends ServiceImpl<LikesBehaviorMapper, LikesBehavior>implements LikesBehaviorService {

    @Autowired
    private BehaviorEntryService behaviorEntryService;

    @Autowired
    private KafkaTemplate kafkaTemplate;


    @Override
    public ResponseResult like(LikesBehaviorDto dto) {
        // 1.检查参数
        if (dto == null || dto.getArticleId() == null ||  (dto.getType() < 0 && dto.getType() > 2) || (dto.getOperation() < 0 && dto.getOperation() > 1)){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.查询行为实体
        User user = AppThreadLocalUtils.getUser();
        BehaviorEntry behaviorEntry = behaviorEntryService.findByUserIdOrEquipmentId(user.getId(), dto.getEquipmentId());
        if (behaviorEntry == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.点赞或取消点赞
        LikesBehavior likesBehavior = getOne(Wrappers.<LikesBehavior>lambdaQuery()
                .eq(LikesBehavior::getArticleId, dto.getArticleId())
                .eq(LikesBehavior::getEntryId, behaviorEntry.getId()));
        // 发送消息
        if (dto.getOperation() == 0){
            UpdateArticleMess mess = new UpdateArticleMess();
            mess.setAdd(1);
            mess.setArticleId(dto.getArticleId());
            mess.setType(UpdateArticleMess.UpdateArticleType.LIKES);
            kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(mess));
        }

        if (likesBehavior == null && dto.getOperation() == 0){
             likesBehavior = new LikesBehavior();
             likesBehavior.setOperation(dto.getOperation());
             likesBehavior.setArticleId(dto.getArticleId());
             likesBehavior.setEntryId(behaviorEntry.getId());
             likesBehavior.setType(dto.getType());
             likesBehavior.setCreatedTime(new Date());
             save(likesBehavior);
             return ResponseResult.okResult(ResultEnum.SUCCESS);
        }else {
            // 取消逻辑
            // 有值的话 就 更新Operation数值为1
            likesBehavior.setOperation(dto.getOperation());
            updateById(likesBehavior);
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        }
    }
}
