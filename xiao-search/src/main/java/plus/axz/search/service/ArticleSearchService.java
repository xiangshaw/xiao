package plus.axz.search.service;

import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;

import java.io.IOException;

/**
 * @author xiaoxiang
 * description ES文章分页搜索
 */
public interface ArticleSearchService {
    ResponseResult<?> search(UserSearchDto dto) throws IOException;
}
