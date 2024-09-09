package plus.axz.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.SensitiveDto;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.common.dtos.ResponseResult;

import java.util.List;

/**
 * @author xiaoxiang
 * descriptio 敏感词管理
 */
@Api(value = "敏感词管理", tags = "Sensitive")
public interface SensitiveControllerApi {
    /**
     * 根据名称分页查询敏感词
     */
    @ApiOperation("敏感词分页列表查询")
     ResponseResult<?> list(SensitiveDto dto);

    /**
     * 新增
     */
    @ApiOperation("新增敏感词")
     ResponseResult<?> insert(Sensitive sensitives);

    /**
     * 修改
     */
    @ApiOperation("修改敏感词")
     ResponseResult<?> update(Sensitive sensitives);

    /**
     * 删除
     */
    @ApiOperation("删除敏感词")
     ResponseResult<?> deleteById(Integer id);

    @ApiOperation("查所有敏感词")
    List<Sensitive> findAllSensitive();
}
