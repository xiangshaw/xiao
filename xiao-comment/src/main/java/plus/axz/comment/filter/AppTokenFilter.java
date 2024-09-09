package plus.axz.comment.filter;

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
import java.io.IOException;

/**
 * @author xiaoxiang
 * description 评论必须在登录的情况下才能发布，所以需要验证用户获取用户信息,添加过滤类
 */
@Order(1)
@WebFilter(filterName = "appTokenFilter", urlPatterns = "/*")
public class AppTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        // 测试和开发环境不过滤
        String userId = request.getHeader("userId");
        //如果userId为0，说明当前设备没有登录
        if (userId != null && Integer.valueOf(userId).intValue() != 0) {
            User apUser = new User();
            apUser.setId(Integer.valueOf(userId));
            AppThreadLocalUtils.setUser(apUser);
        }
        chain.doFilter(req, res);
    }
}
