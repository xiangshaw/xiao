package plus.axz.comment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * description jackson序列化，阿里云安全，接口Knife4j, 通用异常
 */
@Configuration
@ComponentScan({"plus.axz.common.jackson",
        "plus.axz.common.aliyun",
        "plus.axz.common.exception",
        "plus.axz.common.knife4j"
})
public class InitConfig {
}
