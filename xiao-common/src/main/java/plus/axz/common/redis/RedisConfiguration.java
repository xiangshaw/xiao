package plus.axz.common.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiaoxiang
 * description redis配置类
 */
@Configuration
@PropertySource("classpath:redis.properties")
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfiguration {
}
