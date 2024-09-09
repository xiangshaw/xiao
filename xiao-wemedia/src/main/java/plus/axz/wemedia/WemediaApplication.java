package plus.axz.wemedia;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author xiaoxiang
 * description
 */
// 移除自动数据源配置，采取自定义数据源配置方式
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
/*扫描mapper接口*/
@MapperScan("plus.axz.wemedia.mapper")
@EnableDiscoveryClient
//  @Order(2) 和 @WebFilter 两个注解生效
@ServletComponentScan
public class WemediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class, args);
    }

    /**
     * mybatis-plus分页插件
     * 3.4版本以前
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /*
     3.4版本后
    */
   /* @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(false);
        paginationInnerInterceptor.setMaxLimit(500L);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return mybatisPlusInterceptor;
    }*/
}
