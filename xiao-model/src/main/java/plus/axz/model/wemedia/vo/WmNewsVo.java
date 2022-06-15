package plus.axz.model.wemedia.vo;

import lombok.Data;
import plus.axz.model.wemedia.pojos.WmNews;

/**
 * @author xiaoxiang
 * @date 2022年06月15日
 * @particulars
 * 返回的文章信心中包含了作者信息，但是作者名称并不能在文章表中体现，只有一个用户id，目前需要通过用户id关联查询用户表或许完整数据
 * vo:value object 值对象 / view object 表现层对象，主要对应页面显示（web页面）的数据对象、
 * 需要先封装一个vo类，来存储文章信息和作者名称
 */
@Data
public class WmNewsVo extends WmNews {
    /**
     * 文章作者名称
     */
    private String authorName;
}
