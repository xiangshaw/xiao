package plus.axz.article.service;

import plus.axz.model.article.dtos.CollectionDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 文章收藏
 */
public interface CollectionService {
    ResponseResult<?> collectionBehavior(CollectionDto dto);
}
