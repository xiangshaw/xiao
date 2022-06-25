package plus.axz.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.model.search.pojos.UserSearch;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars 用户搜索服务类
 */
public interface UserSearchService extends IService<UserSearch> {
    /**
     查询搜索历史
     @param userSearchDto
     */
    ResponseResult findUserSearch(UserSearchDto userSearchDto);

    /**
     删除搜索历史
     @param userSearchDto
     */
    ResponseResult delUserSearch(UserSearchDto userSearchDto);

     /**
      * 插入搜索历史
      * @param entryId
      * @param searchWords
      */
    public void insert(Integer entryId,String searchWords);
}
