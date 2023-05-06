package plus.axz.common.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars mongodb连接配置
 */
@Data
@Configuration
@PropertySource("classpath:mongo.properties")
@ConfigurationProperties(prefix = "mongo")
public class MongoDBconfigure {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String dbname;

    /**
     * 构建MongoTemplate
     *
     * @return
     */
    @Bean
    public MongoTemplate getMongoTemplate() {
        return new MongoTemplate(getSimpleMongoDbFactory());
    }

    public SimpleMongoDbFactory getSimpleMongoDbFactory() {
        // 无认证的初始化方法
        // return new SimpleMongoDbFactory(new MongoClient(host, port), dbname);
        // 有认证的初始化方法
        ServerAddress serverAddress = new ServerAddress(host, port);
        List<MongoCredential> mongoCredentialList = new ArrayList<>();
        MongoCredential mongoCredential = MongoCredential.createCredential(username, dbname, password.toCharArray());
        mongoCredentialList.add(mongoCredential);
        return new SimpleMongoDbFactory(new MongoClient(serverAddress, mongoCredentialList), dbname);
    }
}