package plus.axz.model.wemedia.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * description 素材dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WmMaterialDto extends PageRequestDto {
    // 是否收藏 1收藏
    private Short isCollection;
}
