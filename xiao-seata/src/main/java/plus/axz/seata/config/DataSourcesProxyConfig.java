package plus.axz.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author xiaoxiang
 * @date 2022年03月30日
 * @particulars 代理数据源
 */
@Configuration
// 让使用了 @ConfigurationProperties 注解的类生效,并且将该类注入到 IOC 容器中,交由 IOC 容器进行管理
@EnableConfigurationProperties({MybatisPlusProperties.class})
public class DataSourcesProxyConfig {
    @Bean/*把当前方法对象返回值，放入spring容器中管理*/
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();/*创建DruidDataSource，最终目的是创建DataSourceProxy（seata提供的代理源）*/
    }
    //创建代理数据源

    @Primary//@Primary标识必须配置在代码数据源上，否则本地事务失效
    @Bean
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        /*数据源代理，要对数据进行备份、快照，控制当前某一个节点的数据，需要处理你的数据，所以需要一个数据源代理*/
        return new DataSourceProxy(druidDataSource);
    }

    private MybatisPlusProperties properties;

    public DataSourcesProxyConfig(MybatisPlusProperties properties) {
        this.properties = properties;
    }

    //替换SqlSessionFactory的DataSource
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSourceProxy dataSourceProxy, MybatisPlusInterceptor paginationInterceptor) throws Exception {
        // 这里必须用 MybatisSqlSessionFactoryBean 代替了 SqlSessionFactoryBean，否则 MyBatisPlus 不会生效
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSourceProxy);/*放入当前数据源对象*/
        mybatisSqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());/*开启当前本地的事务*/

        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()/*加载一些配置*/
                .getResources("classpath*:/mapper/*.xml"));

        MybatisConfiguration configuration = this.properties.getConfiguration();/*有没有在配置文件加入其他的配置*/
        if(configuration == null){/*有的话就设值进去，没有就跳过*/
            configuration = new MybatisConfiguration();
        }
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);
        //添加分页拦截器,设置分页
        Interceptor[] plugins = {paginationInterceptor};
        mybatisSqlSessionFactoryBean.setPlugins(plugins);

        return mybatisSqlSessionFactoryBean;/*返回对象*/
    }
}
