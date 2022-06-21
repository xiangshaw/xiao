package plus.axz.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.behavior.dtos.ReadBehaviorDto;
import plus.axz.model.behavior.pojos.ReadBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 保存阅读行为
 */
public interface ReadBehaviorService extends IService<ReadBehavior> {
    ResponseResult readBehavior(ReadBehaviorDto dto);
}
