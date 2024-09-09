package plus.axz.model.search.pojos;

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
 * description 用户搜索信息表
 */
@Data
@TableName("user_search")
public class UserSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("entry_id")
    private Integer entryId;

    /**
     * 搜索词
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 当前状态0 无效 1有效
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;

}
