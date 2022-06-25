package plus.axz.search.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.model.search.pojos.UserSearch;
import plus.axz.model.user.pojos.User;
import plus.axz.search.feign.BehaviorFeign;
import plus.axz.search.mapper.UserSearchMapper;
import plus.axz.search.service.UserSearchService;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars 搜索记录
 */
@Service
@Log4j2
public class UserSearchServiceImpl extends ServiceImpl<UserSearchMapper, UserSearch> implements UserSearchService {
    @Override
    public ResponseResult findUserSearch(UserSearchDto userSearchDto) {
        // 1.检查参数
        // 大于50条搜索记录，大于50条搜索记录，搜索框装不下，返回错误
        if (userSearchDto.getPage_size() > 50){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"清除搜索记录后重试");
        }
        // 2.查询行为实体
        BehaviorEntry behaviorEntry = getEntry(userSearchDto);
        if (behaviorEntry == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.分页查询，默认查询5条数据返回
        Page pageNum = new Page(0, userSearchDto.getPage_size());
        // 条件，行为实体id，有效ID ，状态1有效
        Page page = page(pageNum, Wrappers.<UserSearch>lambdaQuery()
                .eq(UserSearch::getEntryId, behaviorEntry.getEntryId())
                .eq(UserSearch::getStatus,1));
        return ResponseResult.okResult(page.getRecords());
    }

    @Autowired
    private BehaviorFeign behaviorFeign;
    /**
     * 获取行为实体
     * @author xiaoxiang
     * @date 2022/6/24
     * @param userSearchDto
     * @return plus.axz.model.behavior.pojos.BehaviorEntry
     */
    private BehaviorEntry getEntry(UserSearchDto userSearchDto) {
        User user = AppThreadLocalUtils.getUser();
        return behaviorFeign.findByUserIdOrEntryId(user.getId(),userSearchDto.getEquipmentId());
    }

    @Override
    public ResponseResult delUserSearch(UserSearchDto userSearchDto) {
        // 1.检查参数
        if (userSearchDto.getId() == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.更新当前搜索记录状态 status 0
        BehaviorEntry entry = getEntry(userSearchDto);
        if (entry == null){
            // 行为实体未查到，返回错误
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        update(Wrappers.<UserSearch>lambdaUpdate()
                .eq(UserSearch::getId,userSearchDto.getId())
                .eq(UserSearch::getEntryId,entry.getEntryId())
                .set(UserSearch::getStatus,0));
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    @Async("taskExecutor") // 开启异步请求，，首页搜索保存的代码 出问题也不影响正常数据返回
    public void insert(Integer entryId, String searchWords) {
    // 1.查询当前搜索记录
        UserSearch userSearch = getOne(Wrappers.<UserSearch>lambdaQuery()
                .eq(UserSearch::getEntryId, entryId)
                .eq(UserSearch::getKeyword, searchWords));
    // 2.如果存在更新状态
        if (userSearch != null && userSearch.getStatus() == 1){
            log.info("当前关键字已存在，无需再次保存");
            return;
        }else if (userSearch != null && userSearch.getStatus() == 0){
            userSearch.setStatus(1);
            updateById(userSearch);
            return;
        }
    // 3.如果不存在，保存新的数据
        UserSearch search = new UserSearch();
        search.setEntryId(entryId);
        search.setKeyword(searchWords);
        search.setStatus(1);
        search.setCreatedTime(new Date());
        save(search);
    }
}
