package plus.axz.model.behavior.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars 收藏行为
 */
@Data
public class CollectionBehaviorDto {
    // 设备ID
    @IdEncrypt
    Integer equipmentId;
    // 文章、动态ID
    @IdEncrypt
    Long articleId;
    /**
     * 收藏内容类型
     * 0文章
     * 1动态
     */
    Short type;

    /**
     * 操作类型
     * 0收藏
     * 1取消收藏
     */
    Short operation;

    Date publishedTime;
}
