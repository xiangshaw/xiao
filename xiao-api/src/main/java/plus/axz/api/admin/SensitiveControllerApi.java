package plus.axz.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.SensitiveDto;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.common.dtos.ResponseResult;

@Api(value = "敏感词管理", tags = "Sensitive", description = "敏感词管理API")
public interface SensitiveControllerApi {
    /**
     * 根据名称分页查询敏感词
     *
     * @param dto
     * @return com.axz.model.common.dtos.ResponseResult
     * @author xiaoxiang
     * @date 2022/3/23
     */
    @ApiOperation("敏感词分页列表查询")
    public ResponseResult list(SensitiveDto dto);

    /**
     * 新增
     *
     * @param sensitives
     * @return
     */
    @ApiOperation("新增敏感词")
    public ResponseResult insert(Sensitive sensitives);

    /**
     * 修改
     *
     * @param sensitives
     * @return
     */
    @ApiOperation("修改敏感词")
    public ResponseResult update(Sensitive sensitives);

    /**
     * 删除敏感词
     *
     * @param id
     * @return
     */
    @ApiOperation("删除敏感词")
    public ResponseResult deleteById(Integer id);
}
