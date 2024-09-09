package plus.axz.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * description 引入Seata工程
 */

@Configuration
@ComponentScan("plus.axz.seata.config")
public class SeataConfig {
}
