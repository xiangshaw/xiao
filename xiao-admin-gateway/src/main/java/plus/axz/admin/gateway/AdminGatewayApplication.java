package plus.axz.admin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaoxiang
 * @date 2022年03月24日
 * @particulars admin网关引导类
 */
@SpringBootApplication // 标识时spring boot项目
@EnableDiscoveryClient // 注册到nacos中,,,就是将一个微服务注册到Eureka Server（或其他服务发现组件，例如Zookeeper、Consul等
public class AdminGatewayApplication {
    public static void main(String[] args){
        SpringApplication.run(AdminGatewayApplication.class,args);
    }
}
