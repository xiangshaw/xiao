package plus.axz.model.behavior.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
@Data
public class UnLikesBehaviorDto {
    // 设备ID
    @IdEncrypt
    Integer equipmentId;
    // 文章ID
    @IdEncrypt
    Long articleId;

    /**
     * 不喜欢操作方式
     * 0 不喜欢
     * 1 取消不喜欢
     */
    Short type;
}
