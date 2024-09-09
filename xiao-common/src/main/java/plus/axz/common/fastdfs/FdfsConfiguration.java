package plus.axz.common.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiaoxiang
 * description FastDFSClient配置类
 */
@Configuration
// 导入FastDFS-Client组件
@Import(FdfsClientConfig.class)
@PropertySource("classpath:fast_dfs.properties")
public class FdfsConfiguration {
}
