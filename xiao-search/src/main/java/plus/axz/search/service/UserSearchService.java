package plus.axz.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.model.search.pojos.UserSearch;

/**
 * @author xiaoxiang
 * description 用户搜索服务类
 */
public interface UserSearchService extends IService<UserSearch> {
    /**
     查询搜索历史
     */
    ResponseResult<?> findUserSearch(UserSearchDto userSearchDto);

    /**
     删除搜索历史
     */
    ResponseResult<?> delUserSearch(UserSearchDto userSearchDto);

     /**
      * 插入搜索历史
      */
    public void insert(Integer entryId,String searchWords);
}
