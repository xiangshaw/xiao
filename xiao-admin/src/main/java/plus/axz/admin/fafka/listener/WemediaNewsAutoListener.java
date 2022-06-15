package plus.axz.admin.fafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import plus.axz.admin.service.WeMediaNewsAutoScanService;
import plus.axz.common.constants.message.NewsAutoScanConstants;

import java.util.Optional;

/**
 * @author xiaoxiang
 * @date 2022年06月15日
 * @particulars 自媒体文章发布以后(生产端)会发消息过来自动进行审核，需要在admin端来接收（消费）消息，处理审核
 */
@Component // 标注一个类为Spring容器的Bean，（把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
public class WemediaNewsAutoListener {
    @Autowired
    WeMediaNewsAutoScanService weMediaNewsAutoScanService;

    @KafkaListener(topics = NewsAutoScanConstants.WM_NEWS_AUTO_SCAN_TOPIC) // 指定同一个topics，接收数据
    public void recevieMessage(ConsumerRecord<?,?> record){
        Optional<? extends ConsumerRecord<?, ?>> optional = Optional.ofNullable(record);// jdk8判空
        if(optional.isPresent()){
            Object value = record.value();
            // 提交之后进行 根据文章id 审核
            weMediaNewsAutoScanService.autoScanByMediaNewsId(Integer.valueOf((String) value));
        }
    }
}
