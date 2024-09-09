package plus.axz.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.TagDto;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 标签管理接口
 */
@Api(value = "标签管理", tags = "tag")
public interface TagControllerApi {

    @ApiOperation("标签分页列表查询")
    ResponseResult<?> findByNameAndPage(TagDto dto);

    @ApiOperation("新增标签")
    ResponseResult<?> save(Tag tag);

    @ApiOperation("修改标签")
    ResponseResult<?> update(Tag tag);

    @ApiOperation("删除标签")
    ResponseResult<?> deleteById(Integer id);

    @ApiOperation("查询所有标签")
    ResponseResult<?> findAll();

    @ApiOperation("标签关键字查询")
    ResponseResult<?> findTagName(String search);
}
