package plus.axz.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * @date 2022年06月15日
 * @particulars 引入Seata
 */
@Configuration
@ComponentScan("plus.axz.seata.config")
public class SeataConfig {
}
