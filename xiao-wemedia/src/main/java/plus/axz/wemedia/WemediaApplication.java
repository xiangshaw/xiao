package plus.axz.wemedia;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author xiaoxiang
 * @date 2022年03月21日
 * @particulars admin微服务引导类
 */
@SpringBootApplication
@MapperScan("plus.axz.wemedia.mapper")/*扫描mapper接口*/
@EnableDiscoveryClient
public class WemediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class,args);
    }
    /**
     * mybatis-plus分页插件
     * 3.4版本以前
     */
    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }*/

    /*
     3.4版本后
    */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(false);
        paginationInnerInterceptor.setMaxLimit(500L);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return mybatisPlusInterceptor;
    }
}
