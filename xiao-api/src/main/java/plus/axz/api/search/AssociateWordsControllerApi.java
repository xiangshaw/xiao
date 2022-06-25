package plus.axz.api.search;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars 联想词
 */
public interface AssociateWordsControllerApi {
    @ApiOperation("联想词")
    ResponseResult search(UserSearchDto userSearchDto);
}
