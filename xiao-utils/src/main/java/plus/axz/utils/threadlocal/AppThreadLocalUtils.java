package plus.axz.utils.threadlocal;

import plus.axz.model.user.pojos.User;

/**
 * @author xiaoxiang
 * description  线程变量工具类
 */
public class AppThreadLocalUtils {

    private AppThreadLocalUtils() {
        throw new IllegalStateException("utility class");
    }

    // 定义一个线程变量，用于存储当前线程中的用户
    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    //设置当前线程中的用户
    public static void setUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    //获取线程中的用户
    public static User getUser() {
        return USER_THREAD_LOCAL.get();
    }

    //清除线程中的用户
    public static void clearUser() {
        USER_THREAD_LOCAL.remove();
    }
}
