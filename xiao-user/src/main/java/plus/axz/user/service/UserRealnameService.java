package plus.axz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.model.user.pojos.UserRealname;

/**
 * @author xiaoxiang
 * description 用户实名认证服务
 */
@Mapper
public interface UserRealnameService extends IService<UserRealname> {
    @ApiOperation("分页查询用户列表")
    ResponseResult<?> loadListByStatus(AuthDto dto);

    @ApiOperation("修改用户认证状态")
    ResponseResult<?> updateStatusById(AuthDto dto, Short status);
}
