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
}
