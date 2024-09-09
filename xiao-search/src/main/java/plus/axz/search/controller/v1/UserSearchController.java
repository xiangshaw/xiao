package plus.axz.search.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.search.UserSearchControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.search.service.UserSearchService;

/**
 * @author xiaoxiang
 * description
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/history")
public class UserSearchController implements UserSearchControllerApi {

    private final UserSearchService userSearchService;

    // 查询搜索历史
    @Override
    @PostMapping("/load")
    public ResponseResult<?> findUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return userSearchService.findUserSearch(userSearchDto);
    }

    // 删除搜索历史
    @Override
    @PostMapping("/del")
    public ResponseResult<?> delUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return userSearchService.delUserSearch(userSearchDto);
    }
}
