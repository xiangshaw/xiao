package plus.axz.api.article;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.article.dtos.CollectionDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 收藏行为接口
 */
public interface CollectionControllerApi {
    @ApiOperation("保存或更新收藏行为")
    ResponseResult<?> collectionBehavior(CollectionDto dto);
}
