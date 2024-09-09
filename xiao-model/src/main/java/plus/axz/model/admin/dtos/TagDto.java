package plus.axz.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * description 标签dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDto extends PageRequestDto {
/*接收三个参数---频道名称-当前页-每页显示条数*/
/*通过继承PageRequestDto，接收当前页-每页显示条数*/
/**
* 标签名称
*/
@ApiModelProperty("标签名称")
private String tagName;
}
