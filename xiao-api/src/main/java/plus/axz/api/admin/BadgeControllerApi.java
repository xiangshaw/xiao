package plus.axz.api.admin;

import io.swagger.annotations.Api;
import plus.axz.model.admin.dtos.BadgeDto;
import plus.axz.model.admin.pojos.Badge;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 徽章管理API
 */
@Api(value = "徽章管理", tags = "badge")
public interface BadgeControllerApi {
    /**
     * 根据名称分页查询徽章列表
     */
    ResponseResult<?> findByNameAndPage(BadgeDto dto);

    /**
     * 新增
     */
    ResponseResult<?> save(Badge badge);

    /**
     * 修改
     */
    ResponseResult<?> update(Badge badge);

    /**
     * 删除
     */
    ResponseResult<?> deleteById(Integer id);

    /**
     * 查询所有徽章
     */
    ResponseResult<?> findAll();

    /**
     * 关键字查询
     */
    ResponseResult<?> findBadgeName(String search);
}
