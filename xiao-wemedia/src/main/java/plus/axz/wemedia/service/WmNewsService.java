package plus.axz.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
 * description 自媒体文章业务类
 */
public interface WmNewsService extends IService<WmNews> {
    /**
     * 分页带条件查询自媒体文章列表
     */
    ResponseResult<?> findAll(WmNewsPageReqDto dto);

    /**
     * 自媒体文章发布
     */
    ResponseResult<?> saveNews(WmNewsDto dto, Short isSubmit);

    /**
     * 根据id查询文章
     */
    ResponseResult<?> findWmNewById(Integer id);

    /**
     * 删除文章
     */

    ResponseResult<?> delNews(Integer id);
    /**
     * 文章上下架
     */
    ResponseResult<?> downOrUp(WmNewsDto dto);

    /**
     * 查询待发布文章
     * admin远程调用，xxl-job
     */
    List<Integer> findRelease();

    /**
     * 根据标题模糊分页查询文章信息
     */
    PageResponseResult findList(NewsAuthDto dto);

    /**
     * 根据文章id查询文章详情
     */
    WmNewsVo findWmNewsVo(Integer id);
}
