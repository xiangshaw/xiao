package plus.axz.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * description 引入exception抓捕全局异常
 */
/*把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器中(应用上下文)*/
@Configuration
@ComponentScan("plus.axz.common.exception")
public class ExceptionConfig {
}
