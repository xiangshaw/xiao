package plus.axz.api.search;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;

import java.io.IOException;

/**
 * @author xiaoxiang
 * description 搜索文章
 */
public interface ArticleSearchControllerApi {
    @ApiOperation("ES分页搜索文章")
     ResponseResult<?> search(UserSearchDto dto) throws IOException;
}
