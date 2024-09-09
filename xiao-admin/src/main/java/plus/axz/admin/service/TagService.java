package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.admin.dtos.TagDto;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description
 */
public interface TagService extends IService<Tag> {
    /**
     * 根据名称分页查询标签列表
     */
    ResponseResult<?> findByNameAndPage(TagDto dto);

    /**
     * 新增
     */
    ResponseResult<?> insert(Tag tag);

    /**
     * 修改
     */
    ResponseResult<?> update(Tag tag);

    /**
     * 删除
     */
    ResponseResult<?> deleteById(Integer id);
}
