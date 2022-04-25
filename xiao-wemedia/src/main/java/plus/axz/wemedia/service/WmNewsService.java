package plus.axz.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmNewsDto;
import plus.axz.model.wemedia.dtos.WmNewsPageReqDto;
import plus.axz.model.wemedia.pojos.WmNews;

/**
 * @author xiaoxiang
 * @date 2022年04月03日
 * @particulars 自媒体文章业务类
 */
public interface WmNewsService extends IService<WmNews> {
    /**
     * 分页带条件查询自媒体文章列表
     * @author xiaoxiang
     * @date 2022/4/3
     * @param dto
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     * 自媒体文章发布
     * @author xiaoxiang
     * @date 2022/4/4
     * @param dto
     * @param isSubmit
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult saveNews(WmNewsDto dto, Short isSubmit);

    /**
     * 根据id查询文章
     * @author xiaoxiang
     * @date 2022/4/25
     * @param id
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult findWmNewById(Integer id);

    /**
     * 删除文章
     * @author xiaoxiang
     * @date 2022/4/25
     * @param id
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult delNews(Integer id);
    /**
     * 文章上下架
     * @author xiaoxiang
     * @date 2022/4/25
     * @param dto
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult downOrUp(WmNewsDto dto);
}
