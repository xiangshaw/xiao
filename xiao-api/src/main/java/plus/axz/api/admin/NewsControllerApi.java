package plus.axz.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.NewsDto;
import plus.axz.model.admin.dtos.TagDto;
import plus.axz.model.admin.pojos.News;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月22日
 * @particulars
 */
public interface NewsControllerApi {

    public ResponseResult save(News news);
    public ResponseResult update(News news);
    public ResponseResult deleteById(Integer id);
    public ResponseResult findAll();
    public ResponseResult findByNameAndPage(NewsDto dto);
    public ResponseResult findNewName(String search);
}
