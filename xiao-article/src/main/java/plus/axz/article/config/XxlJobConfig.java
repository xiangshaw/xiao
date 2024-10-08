package plus.axz.article.config;


import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * description 定时计算热点文章
 */
@Log4j2
@Configuration
public class XxlJobConfig {

    @Value("${xxljob.article.addresses}")
    private String adminAddresses;

    @Value("${xxljob.executor.appname}")
    private String appName;

    @Value("${xxljob.executor.port}")
    private int port;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setLogRetentionDays(30);
        return xxlJobSpringExecutor;
    }
}
