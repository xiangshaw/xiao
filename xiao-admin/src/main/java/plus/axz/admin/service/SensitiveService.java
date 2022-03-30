package plus.axz.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.admin.dtos.SensitiveDto;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.common.dtos.ResponseResult;

public interface SensitiveService extends IService<Sensitive> {
    /**
     * 根据名称分页查询敏感词
     *
     * @param dto
     * @return com.axz.model.common.dtos.ResponseResult
     * @author xiaoxiang
     * @date 2022/3/23
     */
    public ResponseResult list(SensitiveDto dto);

    /**
     * 新增敏感词
     *
     * @param sensitive
     * @return
     */
    public ResponseResult insert(Sensitive sensitive);

    /**
     * 修改敏感词
     *
     * @param sensitive
     * @return
     */
    public ResponseResult update(Sensitive sensitive);

    /**
     * 删除敏感词
     *
     * @param id
     * @return
     */
    public ResponseResult deleteById(Integer id);
}
