package plus.axz.wemedia.filter;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import plus.axz.wemedia.utils.JwtUtil;
import reactor.core.publisher.Mono;

/**
 * @author xiaoxiang
 * @date 2022年03月24日
 * @particulars 全局过滤器
 * 启动admin服务，继续访问其他微服务，会提示需要认证才能访问，这个时候需要在heads中设置token才能正常访问。
 */
@Component
@Log4j2
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求对象和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //2.判断当前的请求是否为登录，如果是，直接放行
        if (request.getURI().getPath().contains("/login/in")) {/*获取请求的url，包含该路径*/
        //直接放行，不走后面的代码
            return chain.filter(exchange);
        }
        //3.获取当前用户的请求头jwt信息
        HttpHeaders headers = request.getHeaders();
        String jwttoken = headers.getFirst("token");/*从请求头中获取jwt*/
        //4.判断当前令牌是否存在
        if (StringUtils.isEmpty(jwttoken)) {
        //如果不存在，向客户端响应错误信息
            response.setStatusCode(HttpStatus.UNAUTHORIZED);/*401*/
            return response.setComplete();//到这，解析完成
        }
        try {/*如果解析异常失败，就捕获错误*/
        //5.如果令牌存在，解析jwt令牌，判断该令牌是否合法，则向客户端返回错误信息
            Claims claims = JwtUtil.getClaimsBody(jwttoken);
            int result = JwtUtil.verifyToken(claims);/*verifyToken验证token是否有效*/
        // -1：有效，0：有效，1：过期，2：过期
            if (result == 0 || result == -1) {
        //5.1合法，则向header中重新设置userId
                Integer id = (Integer) claims.get("id");/*有效的话，从claim中获取信息*/
                log.info("find userid:{} from uri:{}", id, request.getURI());/*打印当前id 和    url*/
        //把当前id重新设值到token中，，，重新设值token到header中
                ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
                    httpHeaders.add("userId", id + "");/*装数据id*/
                }).build();
                exchange.mutate().request(serverHttpRequest).build();//重新设值到当前的链接中,,，build重新构建一下
            }
        } catch (Exception e) {
            e.printStackTrace();/*如果失败，打印异常*/
        //向客户端返回错误提示信息
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //6.放行
        return chain.filter(exchange);
    }

    /**
     * 优先级设置
     * 多个过滤器时，值越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
