package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmNewsDto;
import plus.axz.model.wemedia.dtos.WmNewsPageReqDto;

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
}
