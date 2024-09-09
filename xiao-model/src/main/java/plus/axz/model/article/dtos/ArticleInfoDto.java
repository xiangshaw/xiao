package plus.axz.model.article.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

/**
 * @author xiaoxiang
 * description 加载文章详情
 */
@Data
public class ArticleInfoDto {
    // 设备ID
    @IdEncrypt
    Integer equipmentId;
    // 文章ID
    // 在dto中传递参数的时候如果想要把数值类型转为json，可以使用@IdEncrypt标识字段进行转换
    @IdEncrypt
    private Long articleId;
    // 作者ID
    @IdEncrypt
    Integer authorId;
}
