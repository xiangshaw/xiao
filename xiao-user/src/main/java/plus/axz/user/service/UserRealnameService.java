package plus.axz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.model.user.pojos.UserRealname;

/**
 * @author xiaoxiang
 * @date 2022/3/25
 */
@Mapper
public interface UserRealnameService extends IService<UserRealname> {
    @ApiOperation("分页查询用户列表")
    public ResponseResult loadListByStatus(AuthDto dto);

    @ApiOperation("修改用户认证状态")
    public ResponseResult updateStatusById(AuthDto dto, Short status);
}
