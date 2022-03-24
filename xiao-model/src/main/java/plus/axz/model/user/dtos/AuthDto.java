package plus.axz.model.user.dtos;

import lombok.Data;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * @date 2022年03月24日
 * @particulars
 */
@Data
public class AuthDto extends PageRequestDto {
    /**
     * 认证状态
     * @author xiaoxiang
     * @date 2022/3/24
     */
    private Short status;
    /**
     * 认证id
     * @author xiaoxiang
     * @date 2022/3/24
     */
    private Integer id;
    /**
     * 拒绝的原因
     * @author xiaoxiang
     * @date 2022/3/24
     */
    private String msg;
}
