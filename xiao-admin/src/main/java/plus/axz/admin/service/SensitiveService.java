package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.admin.dtos.SensitiveDto;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 敏感词服务
 */
public interface SensitiveService extends IService<Sensitive> {
    /**
     * 根据名称分页查询敏感词
     */
    ResponseResult<?> list(SensitiveDto dto);

    /**
     * 新增敏感词
     */
    ResponseResult<?> insert(Sensitive sensitive);

    /**
     * 修改敏感词
     */
    ResponseResult<?> update(Sensitive sensitive);

    /**
     * 删除敏感词
     */
    ResponseResult<?> deleteById(Integer id);
}
