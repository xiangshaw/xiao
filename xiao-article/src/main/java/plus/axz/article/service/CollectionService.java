package plus.axz.article.service;

import plus.axz.model.article.dtos.CollectionDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 文章收藏
 */
public interface CollectionService {
    public ResponseResult collectionBehavior(CollectionDto dto);
}
