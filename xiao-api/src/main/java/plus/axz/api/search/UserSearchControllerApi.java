package plus.axz.api.search;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars app端搜索记录
 */
public interface UserSearchControllerApi {

    @ApiOperation("查询搜索历史")
    public ResponseResult findUserSearch(UserSearchDto userSearchDto);

    @ApiOperation("删除搜索历史")
    public ResponseResult delUserSearch(UserSearchDto userSearchDto);
}
