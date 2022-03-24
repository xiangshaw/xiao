package plus.axz.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.axz.model.common.dtos.PageRequestDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensitiveDto extends PageRequestDto {
    /**
     * 敏感词名称
     *
     * @author xiaoxiang
     * @date 2022/3/23
     */
    @ApiModelProperty("敏感词名称")/*描述当前接收参数的信息，，比如该name就是敏感词名称*/
    private String sensitives;

}
