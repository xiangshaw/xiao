package plus.axz.model.admin.dtos;

import lombok.Data;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * @date 2022年06月15日
 * @particulars 根据标题模糊分页查询文章列表信息
 */
@Data
public class NewsAuthDto extends PageRequestDto {
    /**
     * 标题
     * 根据标题模糊分页查询文章列表
     */
    private String title;

    private Integer id;

    /**
     * 审核失败原因
     */
    private String msg;
}
