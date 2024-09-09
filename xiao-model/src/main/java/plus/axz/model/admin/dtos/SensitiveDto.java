package plus.axz.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * description 敏感词dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensitiveDto extends PageRequestDto {
    /**
     * 敏感词名称
     */
    @ApiModelProperty("敏感词名称")
    private String sensitives;
}
