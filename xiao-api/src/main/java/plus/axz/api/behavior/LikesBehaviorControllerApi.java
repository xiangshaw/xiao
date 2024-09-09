package plus.axz.api.behavior;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.behavior.dtos.LikesBehaviorDto;
import plus.axz.model.behavior.pojos.LikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 点赞行为接口
 */
public interface LikesBehaviorControllerApi {
    @ApiOperation("保存点赞行为")
    ResponseResult<?> like(LikesBehaviorDto dto);

    // 点赞远程接口
    @ApiOperation("根据行为实体id和文章id查询点赞行为")
    LikesBehavior findLikeByArticleIdAndEntryId(Long articleId, Integer entryId, Short type);/*type点赞文章内容的类型*/
}
