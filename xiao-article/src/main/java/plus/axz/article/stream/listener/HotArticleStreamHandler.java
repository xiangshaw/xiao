package plus.axz.article.stream.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.stereotype.Component;
import plus.axz.article.config.stream.KafkaStreamListener;
import plus.axz.common.constants.message.HotArticleConstants;
import plus.axz.model.article.mess.ArticleVisitStreamMess;
import plus.axz.model.article.mess.UpdateArticleMess;

import java.util.Arrays;

/**
 * @author xiaoxiang
 * @date 2022年06月26日
 * @particulars 流式处理
 */
@Component
@Slf4j
public class HotArticleStreamHandler implements KafkaStreamListener<KStream<String,String>> {

    @Override
    public String listenerTopic() {
        return HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC;
    }

    @Override
    public String sendTopic() {
        return HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_TOPIC;
    }

    @Override
    public KStream<String, String> getService(KStream<String, String> stream) {
        return stream.flatMapValues(new ValueMapper<String, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(String value) {
                        //UpdateArticleMess
                        UpdateArticleMess updateArticleMess = JSON.parseObject(value, UpdateArticleMess.class);
                        log.info("recevie message value:{}",updateArticleMess);/*控制台打印信息*/
                        //按照文章的id和行为进行聚合查询  --》  1232323232323:LIEKS
                        return Arrays.asList(updateArticleMess.getArticleId()+":"+updateArticleMess.getType());
                    }
                }).map(new KeyValueMapper<String, String, KeyValue<String, String>>() {
                    @Override
                    /*聚合*/
                    public KeyValue<String, String> apply(String key, String value) {
                        return new KeyValue<>(value,value);
                    }
                }).groupByKey().windowedBy(TimeWindows.of(10000))/*10秒聚合一次*/
                .count(Materialized.as("count-article-num-00008"))
                .toStream().map((key,value)->{/*格式化，向外发送消息*/
                    return new KeyValue<>(key.key().toString(),formatObj(key.key().toString(),value.toString()));
                });
    }

    /**
     * 封装消息
     * @param key  1232323232323:LIEKS
     * @param value  5
     * @return
     */
    private String formatObj(String key, String value) {
        ArticleVisitStreamMess mess = new ArticleVisitStreamMess();
        String[] split = key.split(":");
        //文章id
        mess.setArticleId(Long.valueOf(split[0]));
        if(split[1].equals(UpdateArticleMess.UpdateArticleType.LIKES.name())){
            mess.setLike(Long.valueOf(value));
        }
        if(split[1].equals(UpdateArticleMess.UpdateArticleType.VIEWS.name())){
            mess.setView(Long.valueOf(value));
        }
        if(split[1].equals(UpdateArticleMess.UpdateArticleType.COLLECTION.name())){
            mess.setCollect(Long.valueOf(value));
        }
        if(split[1].equals(UpdateArticleMess.UpdateArticleType.COMMENT.name())){
            mess.setComment(Long.valueOf(value));
        }

        log.info("处理之后的结果为:{}",mess);

        return JSON.toJSONString(mess);
    }
}
