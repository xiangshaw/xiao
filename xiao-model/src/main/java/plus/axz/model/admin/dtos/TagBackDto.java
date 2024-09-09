package plus.axz.model.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author xiaoxiang
 * description 后台标签
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagBackDto {
    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 文章量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
