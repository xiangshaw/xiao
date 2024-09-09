package plus.axz.article.kafka.listener;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import plus.axz.article.service.ArticleService;
import plus.axz.common.constants.message.HotArticleConstants;
import plus.axz.model.article.mess.ArticleVisitStreamMess;

import java.util.Optional;

/**
 * @author xiaoxiang
 * description 定义监听，接收聚合之后的数据，文章的分值重新进行计算
 */
@RequiredArgsConstructor
@Component
public class ArticleIncrHandleListener {

    private final ArticleService articleService;

    @KafkaListener(topics = HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_TOPIC)
    public void receiveMessage(ConsumerRecord<?, ?> record) {
        Optional<? extends ConsumerRecord<?, ?>> optional = Optional.ofNullable(record);
        if (optional.isPresent()) {
            ArticleVisitStreamMess mess = JSON.parseObject((String) record.value(), ArticleVisitStreamMess.class);
            articleService.updateArticle(mess);
        }
    }
}
