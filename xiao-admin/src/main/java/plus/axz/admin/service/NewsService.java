package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.admin.dtos.NewsDto;
import plus.axz.model.admin.pojos.News;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月22日
 * @particulars
 */
public interface NewsService extends IService<News> {
    /**
     * 新增
     */
    public ResponseResult insert(News news);
    /**
     * 修改
     */
    public ResponseResult update(News news);
    /**
     * 删除
     */
    public ResponseResult deleteById(Integer id);
    /**
     * 分页查询
     */
    public ResponseResult findByNameAndPage(NewsDto dto);
}
