package plus.axz.model.admin.pojos;

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
 * description 标签
 */
@Data
@TableName("tb_tag")
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标签名称
     */
    @TableField(value = "tag_name")
    private String tagName;
    /**
     * 标签描述
     */
    @TableField("description")
    private String description;
    /**
     * 标签是否默认
     */
    @TableField("is_default")
    private Boolean isDefault;
    @TableField("status")
    private Boolean status;
    /**
     * 默认排序
     */
    @TableField("ord")
    private Integer ord;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
}
