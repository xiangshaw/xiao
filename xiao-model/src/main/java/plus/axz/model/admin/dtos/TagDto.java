package plus.axz.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.axz.model.common.dtos.PageRequestDto;

@Data/*重写get、set方法*/
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDto extends PageRequestDto {
/*接收三个参数---频道名称-当前页-每页显示条数*/
/*通过继承PageRequestDto，接收当前页-每页显示条数*/
/**
* 标签名称
*/
@ApiModelProperty("标签名称")/*描述当前接收参数的信息，，比如该name就是指标签名称*/
private String tagName;
}
