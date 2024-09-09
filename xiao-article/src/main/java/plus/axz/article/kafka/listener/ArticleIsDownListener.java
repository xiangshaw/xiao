package plus.axz.article.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import plus.axz.article.service.ArticleConfigService;
import plus.axz.common.constants.message.WmNewsMessageConstants;
import plus.axz.model.article.pojos.ArticleConfig;

import java.util.Map;
import java.util.Optional;

/**
 * @author xiaoxiang
 * description 自媒体文章发消息通知下架
 */
@RequiredArgsConstructor
@Component
public class ArticleIsDownListener {

    private final ArticleConfigService articleConfigService;

    @KafkaListener(topics = WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void receiveMessage(ConsumerRecord<?, ?> record) {/*消费消息*/
        Optional<? extends ConsumerRecord<?, ?>> optional = Optional.ofNullable(record);
        if (optional.isPresent()) {
            // json数据
            String value = (String) record.value();
            // json数据转为map数据
            Map map = JSON.parseObject(value, Map.class);
            articleConfigService.update(Wrappers.<ArticleConfig>lambdaUpdate()
                    .eq(ArticleConfig::getArticleId, map.get("articleId"))
                    .set(ArticleConfig::getIsDown, map.get("enable")));
        }
    }
}
