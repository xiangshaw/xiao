package plus.axz.api.behavior;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.behavior.dtos.CollectionBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
public interface CollectionBehaviorControllerApi {
    @ApiOperation("保存或更新收藏行为")
    public ResponseResult collectionBehavior(CollectionBehaviorDto dto);
}
