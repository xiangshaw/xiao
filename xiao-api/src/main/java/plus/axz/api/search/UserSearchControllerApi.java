package plus.axz.api.search;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;

/**
 * @author xiaoxiang
 * description app端搜索记录
 */
public interface UserSearchControllerApi {

    @ApiOperation("查询搜索历史")
    ResponseResult<?> findUserSearch(UserSearchDto userSearchDto);

    @ApiOperation("删除搜索历史")
    ResponseResult<?> delUserSearch(UserSearchDto userSearchDto);
}
