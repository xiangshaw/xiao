package plus.axz.api.admin;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 自媒体文章管理接口
    1 需要分页查询自媒体文章信息，可以根据标题模糊查询
    2 当审核通过后，修改文章状态为4
    3 当审核驳回后，修改文章状态为3，并且需要说明原因
    4 需要根据文章id查看文章的详情
 */
public interface NewsAuthControllerApi {

    @ApiOperation("查询自媒体文章列表")
     ResponseResult<?> findNews(NewsAuthDto dto);

    @ApiOperation("查询详情")
     ResponseResult<?> findOne(Integer id);

    @ApiOperation("审核成功")
     ResponseResult<?> authPass(NewsAuthDto dto);

    @ApiOperation("审核失败")
     ResponseResult<?> authFail(NewsAuthDto dto);
}
