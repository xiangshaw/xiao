package plus.axz.model.wemedia.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxiang
 * description 自媒体图文信息表
 */
@Data
@TableName("material")
public class WmMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 自媒体用户ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 图片地址
     */
    @TableField("url")
    private String url;
    /**
     * 素材类型
     0 图片
     1 视频
     */
    @TableField("type")
    private Short type;
    /**
     * 是否收藏
     */
    @TableField("is_collection")
    private Short isCollection;
    /**
     * 创建时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;
}
