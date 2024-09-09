package plus.axz.utils.threadlocal;

import plus.axz.model.wemedia.pojos.WmUser;

/**
 * @author xiaoxiang
 * description  保存登录用户信息
 * 获取登录用户思路分析
 * 在去保存图片之前，需要先知道是哪一位自媒体人登录了当前系统，也就是需要确定哪个自媒体人上传了图片
 * 操作步骤：
 * 1，上传图片需要携带token
 * 2，首先请求到网关服务，解析token是否有效，如果有效，解析后把用户数据设置到下一级请求的header中
 * 3，在自媒体微服务中使用过滤器解析header中的数据，拿到用户数据，设置到当前线程中
 * 4，在具体业务代码中可以从当前线程中获取用户
 * 前面两步都已经实现，主要是在代码中实现后两步即可
 * 在使用过滤器解析后的用户需要放在当前线程中
 */
public class WmThreadLocalUtils {

    private WmThreadLocalUtils() {
        throw new IllegalStateException("Utility class");
    }

    // 定义一个ThreadLocal变量，保存用户信息
    private static final ThreadLocal<WmUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前线程中的用户
     *
     * @param user 当前线程中的自媒体用户
     */
    public static void setUser(WmUser user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取线程中的用户
     */
    public static WmUser getUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 清除当前线程中的用户
     */
    public static void clearUser() {
        USER_THREAD_LOCAL.remove();
    }
}
