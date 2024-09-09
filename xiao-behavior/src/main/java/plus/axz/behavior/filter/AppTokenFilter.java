package plus.axz.behavior.filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;
import plus.axz.model.user.pojos.User;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author xiaoxiang
 * description: 自定义filter，用于获取用户信息，并放到当前线程中
 * 在功能实现的时候需要得到行为实体，所以需要得到当前登录的用户信息，
 * 参考文章微服务，添加filter，获取用户信息放到当前线程中
 */
@Order(1)
@WebFilter(filterName = "appTokenFilter", urlPatterns = "/*")
public class AppTokenFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //得到header中的信息
        String userId = request.getHeader("userId");
        if (userId != null && Integer.valueOf(userId).intValue() != 0) {
            User user = new User();
            user.setId(Integer.valueOf(userId));
            AppThreadLocalUtils.setUser(user);
        }
        filterChain.doFilter(request, response);
    }
}
