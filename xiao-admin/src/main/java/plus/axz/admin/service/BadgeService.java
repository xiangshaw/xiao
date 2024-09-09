package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.admin.dtos.BadgeDto;
import plus.axz.model.admin.pojos.Badge;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 徽章
 */
public interface BadgeService extends IService<Badge> {
    /**
     * 根据名称分页查询徽章列表
     */
    ResponseResult<?> findByNameAndPage(BadgeDto dto);

    /**
     * 新增
     */
    ResponseResult<?> insert(Badge badge);

    /**
     * 修改
     */
    ResponseResult<?> update(Badge badge);

    /**
     * 删除
     */
    ResponseResult<?> deleteById(Integer id);
}
