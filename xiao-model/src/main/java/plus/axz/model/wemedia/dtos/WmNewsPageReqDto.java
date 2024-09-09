package plus.axz.model.wemedia.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.axz.model.common.dtos.PageRequestDto;

import java.util.Date;

/**
 * @author xiaoxiang
 * description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WmNewsPageReqDto extends PageRequestDto {
    //状态
    private Short status;
    //开始时间
    private Date beginPubdate;
    //结束时间
    private Date endPubdate;
    //所属标签ID
    private Integer tagId;
    //关键字
    private String keyWord;
}
