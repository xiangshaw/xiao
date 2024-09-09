package plus.axz.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.model.search.pojos.AssociateWords;

/**
 * @author xiaoxiang
 * description 联想词表 业务类
 */
public interface AssociateWordsService extends IService<AssociateWords> {
    // 联想词
    ResponseResult<?> search(UserSearchDto userSearchDto);
    // 优化联想词
    ResponseResult<?> searchV2(UserSearchDto userSearchDto);
}
