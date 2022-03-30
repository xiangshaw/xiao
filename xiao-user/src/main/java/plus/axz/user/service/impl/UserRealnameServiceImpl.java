package plus.axz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.common.constants.user.UserConstants;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.model.user.pojos.UserRealname;
import plus.axz.user.feign.WemediaFeign;
import plus.axz.user.mapper.UserMapper;
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

    @Override
    public ResponseResult updateStatusById(AuthDto dto, Short status) {
        // 1.  检查参数
        if (dto == null || dto.getId() == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 检查状态    /*0创建中    1待审核    2审核失败    9审核通过*/
        if (checkStatus(status)){
            // 返回true，参数无效
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2. 修改状态
        UserRealname userRealname = new UserRealname();
        //调用mybatis-plus方法updateByid 根据id修改
        userRealname.setId(dto.getId());
        userRealname.setStatus(status);
        // 状态为2 可添加拒绝原因
        if (dto.getMsg() != null){
            // 装入拒绝原因
            userRealname.setReason(dto.getMsg());
        }
        updateById(userRealname);
        // 3. 如果审核状态是9通过，创建自媒体账户，创建作者信息
        if (status.equals(UserConstants.PASS_AUTH)){
            //9通过，创建自媒体账户和作者信息
            ResponseResult result = createWmUserAndAuthor(dto);
            if (result != null){
                return result;
            }
        }
        // 4.
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Autowired
    private UserMapper userMapper;
    //注入远程接口
    @Autowired
    private WemediaFeign wemediaFeign;
    private ResponseResult createWmUserAndAuthor(AuthDto dto) {
        // DOTO 等下写
        return null;
    }

    /**
     * 检查状态
     * @author xiaoxiang
     * @date 2022/3/25
     * @param status
     * @return boolean
     */
    private boolean checkStatus(Short status) {
        // 如果status==null 或者    status不等于2 并且    不等于9
        if (status == null || (!status.equals(UserConstants.FAIL_AUTH) && !status.equals(UserConstants.PASS_AUTH))){
            return true;
        }
        return false;
    }
}
