package plus.axz.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * description admin分页查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto extends PageRequestDto {
    @ApiModelProperty("admin名称")
    private String author;
}
