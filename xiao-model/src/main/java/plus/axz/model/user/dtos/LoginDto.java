package plus.axz.model.user.dtos;

import lombok.Data;

/**
 * @author xiaoxiang
 * description 登录dto
 */
@Data
public class LoginDto {
    // 设备id
    private Integer equipmentId;

    // 手机号
    private String phone;

    // 密码
    private String password;
}
