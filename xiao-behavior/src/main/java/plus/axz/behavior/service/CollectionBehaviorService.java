package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.article.pojos.Collection;
import plus.axz.model.behavior.dtos.CollectionBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 收藏行为服务类
 */
public interface CollectionBehaviorService extends IService<Collection> {
    ResponseResult collectionBehavior(CollectionBehaviorDto dto);
}
