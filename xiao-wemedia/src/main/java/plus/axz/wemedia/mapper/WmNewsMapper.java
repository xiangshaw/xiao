package plus.axz.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.wemedia.pojos.WmNews;
import plus.axz.model.wemedia.vo.WmNewsVo;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年04月03日
 * @particulars 自媒体文章
 */
@Mapper
public interface WmNewsMapper extends BaseMapper<WmNews> {

    /**
     * 多表关联查询
     * 不管是查看文章列表或者是查询文章详情，都需要返回带作者的文章信息，需要关联查询获取数据，
     * 而mybatis-plus暂时不支持多表查询，需要手动定义mapper文件实现
     *
     * @param dto
     * @return
     */
    List<WmNewsVo> findListAndPage(@Param("dto") NewsAuthDto dto);

    int findListCount(@Param("dto") NewsAuthDto dto);
}
