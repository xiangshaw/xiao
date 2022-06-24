package plus.axz.api.search;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;

import java.io.IOException;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars 搜索文章
 */
public interface ArticleSearchControllerApi {
    @ApiOperation("ES分页搜索文章")
    public ResponseResult search(UserSearchDto dto) throws IOException;
}
