package plus.axz.model.user.dtos;

import lombok.Data;

/**
 * @author xiaoxiang
 * @date 2022年08月10日
 * @particulars
 */
@Data
public class ULoginDto {
    // 手机号
    private String phone;

    // 密码
    private String password;
}
