package plus.axz.wemedia.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;
import plus.axz.model.wemedia.pojos.WmUser;
import plus.axz.utils.threadlocal.WmThreadLocalUtils;

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
 * description 过滤器解析header数据并设置到当前线程中
 */
@Order(1)
@WebFilter(filterName = "wmTokenFilter", urlPatterns = "/*")
@Log4j2
public class WmTokenFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //得到header中的信息
        String userId = servletRequest.getHeader("userId");
        if (userId != null) {
            WmUser wmUser = new WmUser();
            wmUser.setId(Integer.valueOf(userId));
            WmThreadLocalUtils.setUser(wmUser);
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
