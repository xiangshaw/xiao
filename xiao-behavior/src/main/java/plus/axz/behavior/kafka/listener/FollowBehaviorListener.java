package plus.axz.behavior.kafka.listener;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import plus.axz.behavior.service.FollowBehaviorService;
import plus.axz.common.constants.message.FollowBehaviorConstants;
import plus.axz.model.behavior.dtos.FollowBehaviorDto;

import java.util.Optional;

/**
 * @author xiaoxiang
 * description 消息监听
 */
@RequiredArgsConstructor
@Component
public class FollowBehaviorListener {

    private final FollowBehaviorService followBehaviorService;

    @KafkaListener(topics = FollowBehaviorConstants.FOLLOW_BEHAVIOR_TOPIC)
    public void receivMessage(ConsumerRecord<?, ?> record) {
        //接收关注行为数据，保存
        Optional<? extends ConsumerRecord<?, ?>> optional = Optional.ofNullable(record);
        if (optional.isPresent()) {
            FollowBehaviorDto dto = JSON.parseObject(record.value().toString(), FollowBehaviorDto.class);
            followBehaviorService.saveFollowBehavior(dto);
        }
    }
}
