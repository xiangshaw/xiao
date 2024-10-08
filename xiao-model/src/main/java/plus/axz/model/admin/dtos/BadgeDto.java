package plus.axz.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDto extends PageRequestDto {
    /*接收三个参数---名称-当前页-每页显示条数*/
    /*通过继承PageRequestDto，接收当前页-每页显示条数*/
    /**
     * badge_name名称
     */
    /*描述当前接收参数的信息，比如该name就是指标签名称*/
    @ApiModelProperty("徽章名称")
    private String badgeName;
}
