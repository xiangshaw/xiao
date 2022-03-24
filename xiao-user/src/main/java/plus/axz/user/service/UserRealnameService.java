package plus.axz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.model.user.pojos.UserRealname;

@Mapper
public interface UserRealnameService extends IService<UserRealname> {
    /**
     * 按照状态分页查询用户列表
     * @author xiaoxiang
     */
    @ApiOperation("分页查询用户列表")
    public ResponseResult loadListByStatus(AuthDto dto);
}
