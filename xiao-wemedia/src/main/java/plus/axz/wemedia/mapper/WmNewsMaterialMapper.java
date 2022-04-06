package plus.axz.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.model.wemedia.pojos.WmNewsMaterial;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年04月02日
 * @particulars
 * 图片处理
 */
@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {
    /**
     * 自定义批量保存，用于素材与文章关系做关联
     */
     void saveRelations(@Param("materials") List<String> materialsIds,
                        @Param("newsId") Integer newsId,
                        @Param("type") Short type);
}
