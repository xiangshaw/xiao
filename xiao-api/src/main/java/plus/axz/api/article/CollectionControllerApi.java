package plus.axz.api.article;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.article.dtos.CollectionDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
public interface CollectionControllerApi {
    @ApiOperation("保存或更新收藏行为")
    public ResponseResult collectionBehavior(CollectionDto dto);
}
