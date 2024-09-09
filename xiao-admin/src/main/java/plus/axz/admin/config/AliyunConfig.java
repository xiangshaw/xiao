package plus.axz.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxiang
 * description 引入阿里云配置，添加fastdfs的支持，方便测试图片检测
 */
@Configuration
@ComponentScan("plus.axz.common.aliyun")
public class AliYunConfig {
}
