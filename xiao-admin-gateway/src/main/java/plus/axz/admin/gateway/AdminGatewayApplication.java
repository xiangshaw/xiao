package plus.axz.admin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaoxiang
 * description admin网关引导类
 */
// 标识是一个spring boot应用
@SpringBootApplication
// 注册到nacos中，就是将一个微服务注册到Eureka Server（或其他服务发现组件，例如Zookeeper、Consul等
@EnableDiscoveryClient
public class AdminGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminGatewayApplication.class, args);
    }
}
