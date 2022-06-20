package plus.axz.utils.threadlocal;

import plus.axz.model.user.pojos.User;

public class AppThreadLocalUtils {
    private final static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    //设置当前线程中的用户
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    //获取线程中的用户
    public static User getUser() {
        return userThreadLocal.get();
    }

}
