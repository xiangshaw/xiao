package plus.axz.behavior.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * description
 */
@Configuration
// 通用异常和jackson
@ComponentScan({"plus.axz.common.jackson","plus.axz.common.exception"})
public class InitConfig {
}
