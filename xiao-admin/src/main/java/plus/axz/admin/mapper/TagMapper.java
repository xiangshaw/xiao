package plus.axz.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.model.admin.dtos.TagBackDto;
import plus.axz.model.admin.pojos.Tag;

import java.util.List;

/**
 * 标签类别Mapper
 * @author xiaoxiang
 * @date 2022/3/22 8:34
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
