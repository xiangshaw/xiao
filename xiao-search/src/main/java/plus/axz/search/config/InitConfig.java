package plus.axz.search.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars
 */
@Configuration
@ComponentScan({"plus.axz.common.jackson",
        "plus.axz.common.aliyun",
        "plus.axz.common.exception",
        "plus.axz.common.knife4j",
        "plus.axz.common.threadpool"})
/*jackson序列化，阿里云安全，通用异常,接口Knife4j,线程池*/
public class InitConfig {
}
