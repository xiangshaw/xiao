package plus.axz.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.model.admin.pojos.Sensitive;

import java.util.List;

/**
 * @author xiaoxiang
 * description 敏感词mapper接口
 */
@Mapper
public interface SensitiveMapper extends BaseMapper<Sensitive> {
    /**
     * 这里查询所有敏感词的作用是，在去审核文本内容的时候，
     * 从两方面入手，一个是调用阿里云第三方接口审核内容，另外一个是系统
     * 自己审核，使用自己维护的敏感词库去匹配发布文章的内容。
     */
    List<String> findAllSensitive();
}
