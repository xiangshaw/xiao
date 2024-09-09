package plus.axz.model.behavior.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxiang
 * description app阅读行为表
 */
@Data
@TableName("read_behavior")
public class ReadBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("entry_id")
    private Integer entryId;

    /**
     * 文章ID
     */
    @TableField("article_id")
    @IdEncrypt
    private Long articleId;

    /**
     * 阅读次数
     */
    @TableField("count")
    private Short count;

    /**
     * 阅读时间单位秒
     */
    @TableField("read_duration")
    private Long readDuration;

    /**
     * 阅读百分比
     */
    @TableField("percentage")
    private Short percentage;

    /**
     * 文章加载时间
     */
    @TableField("load_duration")
    private Long loadDuration;

    /**
     * 登录时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updatedTime;
}
