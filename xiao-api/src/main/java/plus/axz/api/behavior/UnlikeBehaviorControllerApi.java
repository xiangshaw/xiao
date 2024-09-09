package plus.axz.api.behavior;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.behavior.dtos.UnLikesBehaviorDto;
import plus.axz.model.behavior.pojos.UnlikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 保存不喜欢行为
 */
public interface UnlikeBehaviorControllerApi {
    @ApiOperation("不喜欢行为")
    ResponseResult<?> unlikeBehavior(UnLikesBehaviorDto dto);

    // 远程接口
    @ApiOperation("根据行为实体id和文章id查询不喜欢行为")
    UnlikesBehavior findUnLikeByArticleIdAndEntryId(Integer entryId, Long articleId);
}
