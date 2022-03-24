package plus.axz.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.TagDto;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月22日
 * @particulars 标签类别对外API
 */
@Api(value = "标签管理", tags = "tag", description = "标签管理API")
public interface TagControllerApi {
    /**
     * 根据名称分页查询标签列表
     * @param dto
     * @return
     */
    @ApiOperation("标签分页列表查询")
    public ResponseResult findByNameAndPage(TagDto dto);
    /**
     * 新增
     * @param tag
     * @return
     */
    @ApiOperation("新增标签")
    public ResponseResult save(Tag tag);
    /**
     * 修改
     * @param tag
     * @return
     */
    @ApiOperation("修改标签")
    public ResponseResult update(Tag tag);
    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation("删除标签")
    public ResponseResult deleteById(Integer id);

    /**
     * 查询所有标签
     * @return
     */
    @ApiOperation("查询所有标签")
    public ResponseResult findAll();

    /**
     * 关键字查询
     */
    @ApiOperation("标签关键字查询")
    public ResponseResult findTagName(String search);
}
