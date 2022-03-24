package plus.axz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.model.user.pojos.UserRealname;
import plus.axz.user.mapper.UserRealnameMapper;
import plus.axz.user.service.UserRealnameService;

/**
 * @author xiaoxiang
 * @date 2022年03月24日
 * @particulars 查询认证用户
 */

@Service
public class UserRealnameServiceImpl extends ServiceImpl<UserRealnameMapper, UserRealname> implements UserRealnameService {
    @Override
    public ResponseResult loadListByStatus(AuthDto dto) {
        // 1.检查参数
        if (dto ==null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 分页检查
        dto.checkParam();
        // 2.根据状态分页查询
        LambdaQueryWrapper<UserRealname> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (dto.getStatus() != null){
            // 获取状态
            lambdaQueryWrapper.eq(UserRealname::getStatus,dto.getStatus());
        }
        // 分页条件构建
        Page pageParam = new Page(dto.getPage(), dto.getSize());
        // 需要 分页对象 和 状态
        Page page = page(pageParam, lambdaQueryWrapper);

        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        pageResponseResult.setData(page.getRecords());
        return pageResponseResult;
    }
}
