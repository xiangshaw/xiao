package plus.axz.article.config.stream;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.StreamsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiaoxiang
 * description KafkaStreamListener扫描和实例化成KafkaStreamProcessor.doAction的返回类，完成监听器实际注册的过程
 */
@RequiredArgsConstructor
@Component
public class KafkaStreamListenerFactory implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(KafkaStreamListenerFactory.class);

    private final DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * 初始化完成后自动调用
     */
    @Override
    public void afterPropertiesSet() {
        Map<String, KafkaStreamListener> map = defaultListableBeanFactory.getBeansOfType(KafkaStreamListener.class);
        for (String key : map.keySet()) {
            KafkaStreamListener k = map.get(key);
            KafkaStreamProcessor processor = new KafkaStreamProcessor(defaultListableBeanFactory.getBean(StreamsBuilder.class), k);
            String beanName = k.getClass().getSimpleName() + "AutoProcessor";
            defaultListableBeanFactory.registerSingleton(beanName, processor.doAction());
            logger.info("add kafka stream auto listener [{}]", beanName);
        }
    }
}
