package plus.axz.common.constants.user;

/**
 * @author xiaoxiang
 * description 用户常量类
 */
public class UserConstants {

    private UserConstants() {
        throw new IllegalStateException("Utility class");
    }

    /*实名审核成功*/
    public static final Short PASS_AUTH = 9;
    /*实名审核失败*/
    public static final Short FAIL_AUTH = 2;
    /*平台自媒体人*/
    public static final Integer AUTH_TYPE = 2;
}
