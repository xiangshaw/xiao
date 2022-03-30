package plus.axz.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * @date 2022年03月26日
 * @Description: admin分页查询
 */
@Data/*重写get、set方法*/
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto extends PageRequestDto {
    @ApiModelProperty("admin名称")
    private String author;
}
