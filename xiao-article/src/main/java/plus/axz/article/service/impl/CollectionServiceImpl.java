package plus.axz.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import plus.axz.article.feign.BehaviorFeign;
import plus.axz.article.mapper.CollectionMapper;
import plus.axz.article.service.CollectionService;
import plus.axz.common.constants.message.HotArticleConstants;
import plus.axz.model.article.dtos.CollectionDto;
import plus.axz.model.article.mess.UpdateArticleMess;
import plus.axz.model.article.pojos.Collection;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
    @Autowired
    private BehaviorFeign behaviorFeign;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public ResponseResult collectionBehavior(CollectionDto dto) {
        // 1.检查参数
        if (dto == null || dto.getArticleId() == null || (dto.getType() < 0 && dto.getType() > 1) || (dto.getOperation() < 0 && dto.getOperation() > 1)) {
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE);
        }
        // 2.查询行为实体
        User user = AppThreadLocalUtils.getUser();
        BehaviorEntry behaviorEntry = behaviorFeign.findByUserIdOrEntryId(user.getId(), dto.getEquipmentId());
        if (behaviorEntry == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.保存或更新收藏行为
        Collection collection = getOne(Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getEntryId, behaviorEntry.getId())
                .eq(Collection::getArticleId, dto.getArticleId()));
        if (collection == null && dto.getOperation() == 0){
            collection = new Collection();
            collection.setArticleId(dto.getArticleId());
            collection.setEntryId(behaviorEntry.getId());
            collection.setType(dto.getType());
            collection.setOperation(dto.getOperation());
            collection.setCollectionTime(new Date());
            save(collection);
            // 发送消息
            UpdateArticleMess mess = new UpdateArticleMess();
            mess.setAdd(1);
            mess.setArticleId(dto.getArticleId());
            mess.setType(UpdateArticleMess.UpdateArticleType.COLLECTION);
            kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(mess));
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        } else {
            // 有值就更新
            collection.setOperation(dto.getOperation());
            updateById(collection);
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        }
    }
}
