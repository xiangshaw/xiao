package plus.axz.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan /*生效拦截token 获取线程中用户*/
@EnableFeignClients /*远程调用接口*/
public class CommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class, args);
    }
}
