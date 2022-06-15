package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmNewsDto;
import plus.axz.model.wemedia.dtos.WmNewsPageReqDto;
import plus.axz.model.wemedia.pojos.WmNews;
import plus.axz.model.wemedia.vo.WmNewsVo;

import java.util.List;

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

    /**
     * 查询待发布文章
     * admin远程调用，xxl-job
     * @return
     */
    @ApiOperation("查询待发布文章")
    List<Integer> findRelease();

    @ApiOperation("根据标题模糊分页查询文章列表信息")
    public PageResponseResult findList(NewsAuthDto dto);

    @ApiOperation("根据文章id查询文章详情")
    public WmNewsVo findWmNewsVo(Integer id) ;
}
