package plus.axz.api.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import plus.axz.model.admin.dtos.BadgeDto;
import plus.axz.model.admin.pojos.Badge;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月22日
 * @particulars
 */
@Api(value = "徽章管理", tags = "badge", description = "徽章管理API")
public interface BadgeControllerApi{
    /**
     * 根据名称分页查询徽章列表
     */
    public ResponseResult findByNameAndPage(BadgeDto dto);
    /**
     * 新增
     */
    public ResponseResult save(Badge badge);
    /**
     * 修改
     */
    public ResponseResult update(Badge badge);
    /**
     * 删除
     */
    public ResponseResult deleteById(Integer id);
    /**
     * 查询所有徽章
     */
    public ResponseResult findAll();

    /**
     * 关键字查询
     */
    public ResponseResult findBadgeName(String search);
}
