package plus.axz.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * description 引入exception抓捕全局异常
 */
@Configuration/*把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器中(应用上下文)*/
@ComponentScan("plus.axz.common.exception")
public class ExceptionConfig {
}
