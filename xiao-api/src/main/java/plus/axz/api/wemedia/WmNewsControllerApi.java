package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmNewsDto;
import plus.axz.model.wemedia.dtos.WmNewsPageReqDto;
import plus.axz.model.wemedia.pojos.WmNews;

/**
 * @author xiaoxiang
 * @date 2022年04月03日
 * @particulars 自媒体文章信息
 */
public interface WmNewsControllerApi {
    @ApiOperation("分页带条件查询自媒体文章列表")
    public ResponseResult findAll(WmNewsPageReqDto dto);

    @ApiOperation("保存、修改文章，保存草稿")
    public ResponseResult saveNews(WmNewsDto dto);

    @ApiOperation("根据id获取文章信息")
    public ResponseResult findWmNewById(Integer id);

    @ApiOperation("删除文章")
    public ResponseResult delNews(Integer id);

    @ApiOperation("文章上下架")
    public ResponseResult downOrUp(WmNewsDto dto);

    /**
     * 根据id查询文章信息
     * admin远程调用
     */
    @ApiOperation("根据id查询文章信息")
    WmNews findById(Integer id);
    /**
     * 修改文章
     * admin远程调用
     */
    @ApiOperation("修改文章")
    ResponseResult updateWmNews(WmNews wmNews);
}
