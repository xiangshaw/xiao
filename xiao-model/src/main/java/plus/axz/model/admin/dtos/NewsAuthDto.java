package plus.axz.model.admin.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * description 根据标题模糊分页查询文章列表信息
 */
@EqualsAndHashCode(callSuper = true)
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
