package plus.axz.admin;

import org.springframework.util.DigestUtils;

/**
 * @author xiaoxiang
 * @date 2022年03月23日
 * @particulars 手动创建用户进行登录测试使用
 */
public class UserCreate {
    public static void main(String[] args) {
        String salt = "123456";
        String pswd = "123456" + salt;
        // ea48576f30be1669971699c09ad05c94
        String saltPswd = DigestUtils.md5DigestAsHex(pswd.getBytes());
        System.out.println(saltPswd);
        /*
        解析：
        salt:123456
        password:fa0afac6773f737290c5f9d857dd9cbc
        username:axz
        INSERT INTO ad_user(NAME,PASSWORD,salt) VALUES("axz","fa0afac6773f737290c5f9d857dd9cbc","123456")
        {"name":"axz","password":"axz"}
        */
    }
}
