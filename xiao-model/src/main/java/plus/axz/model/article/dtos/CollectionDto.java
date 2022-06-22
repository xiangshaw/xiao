package plus.axz.model.article.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars
 */
@Data
public class CollectionDto {
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
