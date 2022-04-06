package plus.axz.model.wemedia.dtos;

import lombok.Data;
import plus.axz.model.common.dtos.PageRequestDto;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年04月03日
 * @particulars
 */
@Data
public class WmNewsPageReqDto extends PageRequestDto {
    private Short status;//状态
    private Date beginPubdate;//开始时间
    private Date endPubdate;//结束时间
    private Integer tagId;//所属标签ID
    private String keyWord;//关键字
}
