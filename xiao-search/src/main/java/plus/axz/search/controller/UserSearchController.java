package plus.axz.search.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2022年06月24日
 * @particulars
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/history")
public class UserSearchController implements UserSearchControllerApi {
    @Autowired
    private UserSearchService userSearchService;

    @Override
    @PostMapping("/load")/*查询搜索历史*/
    public ResponseResult findUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return userSearchService.findUserSearch(userSearchDto);
    }

    @Override
    @PostMapping("/del")/* 删除搜索历史*/
    public ResponseResult delUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return userSearchService.delUserSearch(userSearchDto);
    }
}
