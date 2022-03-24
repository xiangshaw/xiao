package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.admin.dtos.TagDto;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月22日
 * @particulars
 */
public interface TagService extends IService<Tag> {
    /**
     * 根据名称分页查询标签列表
     * @param dto
     * @return
     */
    public ResponseResult findByNameAndPage(TagDto dto);
    /**
     * 新增
     * @author xiaoxiang
     * @date 2022/3/22 8:41
     * @param tag
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult insert(Tag tag);
    /**
     * 修改
     * @author xiaoxiang
     * @date 2022/3/22 8:43
     * @param tag
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult update(Tag tag);
    /**
     * 删除
     * @author xiaoxiang
     * @date 2022/3/22 8:44
     * @param id
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult deleteById(Integer id);
}
