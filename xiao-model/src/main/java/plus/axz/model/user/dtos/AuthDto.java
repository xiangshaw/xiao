package plus.axz.model.user.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * description 认证dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthDto extends PageRequestDto {
    /**
     * 认证状态
     */
    private Short status;
    /**
     * 认证id
     */
    private Integer id;
    /**
     * 拒绝的原因
     */
    private String msg;
}
