package plus.axz.behavior.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.behavior.mapper.CollectionBehaviorMapper;
import plus.axz.behavior.service.BehaviorEntryService;
import plus.axz.behavior.service.CollectionBehaviorService;
import plus.axz.model.article.pojos.Collection;
import plus.axz.model.behavior.dtos.CollectionBehaviorDto;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 收藏行为
 */
@Service
@Log4j2
public class CollectionBehaviorServiceImpl extends ServiceImpl<CollectionBehaviorMapper, Collection> implements CollectionBehaviorService {

    @Autowired
    private BehaviorEntryService behaviorEntryService;

    @Override
    public ResponseResult collectionBehavior(CollectionBehaviorDto dto) {
        // 1. 检查参数
        if (dto == null || dto.getArticleId() == null || (dto.getType() < 0 && dto.getType() > 1) || (dto.getOperation() < 0 && dto.getOperation() > 1))
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE);
        // 2.查询行为实体
        User user = AppThreadLocalUtils.getUser();
        BehaviorEntry behaviorEntry = behaviorEntryService.findByUserIdOrEquipmentId(user.getId(), dto.getEquipmentId());
        if (behaviorEntry == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.保存或更新收藏行为
        Collection collection = getOne(Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getEntryId, behaviorEntry.getId())
                .eq(Collection::getArticleId, dto.getArticleId()));
        if (collection == null && dto.getOperation() == 0) {
            collection = new Collection();
            collection.setArticleId(dto.getArticleId());
            collection.setEntryId(behaviorEntry.getId());
            collection.setType(dto.getType());
            collection.setOperation(dto.getOperation());
            collection.setCollectionTime(new Date());
            save(collection);
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        } else {
            // 有值就更新
            collection.setOperation(dto.getOperation());
            updateById(collection);
            return ResponseResult.okResult(ResultEnum.SUCCESS);
        }
    }
}
