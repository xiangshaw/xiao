package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.admin.dtos.NewsDto;
import plus.axz.model.admin.pojos.News;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description
 */
public interface NewsService extends IService<News> {
    /**
     * 新增
     */
    ResponseResult<?> insert(News news);

    /**
     * 修改
     */
    ResponseResult<?> update(News news);

    /**
     * 删除
     */
    ResponseResult<?> deleteById(Integer id);

    /**
     * 分页查询
     */
    ResponseResult<?> findByNameAndPage(NewsDto dto);
}
