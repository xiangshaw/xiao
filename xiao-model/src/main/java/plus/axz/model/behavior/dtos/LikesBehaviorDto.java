package plus.axz.model.behavior.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

/**
 * @author xiaoxiang
 * description 点赞行为
 */
@Data
public class LikesBehaviorDto {
    // 设备ID
    // 将equipmentId转为Integer类型
    @IdEncrypt
    Integer equipmentId;
    // 文章、动态、评论等ID
    // 将articleId转为Long类型
    @IdEncrypt
    Long articleId;
    /**
     * 喜欢内容类型
     * 0文章
     * 1动态
     * 2评论
     */
    Short type;

    /**
     * 喜欢操作方式
     * 0 点赞
     * 1 取消点赞
     */
    Short operation;
}
