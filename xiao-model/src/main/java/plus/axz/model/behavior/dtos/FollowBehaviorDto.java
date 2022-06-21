package plus.axz.model.behavior.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars 发送消息需要准备一个FollowBehaviorDto,进行数据的传递
 */
@Data
public class FollowBehaviorDto {
    //文章id
    @IdEncrypt
    Long articleId;
    //关注的id
    Integer followId;
    //用户id
    Integer userId;
}
