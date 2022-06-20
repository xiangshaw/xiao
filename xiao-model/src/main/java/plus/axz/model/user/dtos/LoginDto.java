package plus.axz.model.user.dtos;

import lombok.Data;

/**
 * @author xiaoxiang
 * @date 2022年06月19日
 * @particulars
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
