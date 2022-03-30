package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.admin.dtos.BadgeDto;
import plus.axz.model.admin.pojos.Badge;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月29日
 * @particulars
 */
public interface BadgeService extends IService<Badge> {
    /**
     * 根据名称分页查询徽章列表
     */
    public ResponseResult findByNameAndPage(BadgeDto dto);
    /**
     * 新增
     */
    public ResponseResult insert(Badge badge);
    /**
     * 修改
     */
    public ResponseResult update(Badge badge);
    /**
     * 删除
     */
    public ResponseResult deleteById(Integer id);
}
