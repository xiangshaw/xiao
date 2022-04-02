package plus.axz.model.wemedia.dtos;

import lombok.Data;
import plus.axz.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * @date 2022年04月02日
 * @particulars
 */
@Data
public class WmMaterialDto extends PageRequestDto {
    // 是否收藏 1收藏
    private Short isCollection;
}
