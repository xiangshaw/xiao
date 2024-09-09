package plus.axz.api.admin;

import plus.axz.model.admin.dtos.NewsDto;
import plus.axz.model.admin.pojos.News;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 新闻管理接口
 */
public interface NewsControllerApi {

    ResponseResult<?> save(News news);

    ResponseResult<?> update(News news);

    ResponseResult<?> deleteById(Integer id);

    ResponseResult<?> findAll();

    ResponseResult<?> findByNameAndPage(NewsDto dto);

    ResponseResult<?> findNewName(String search);
}
