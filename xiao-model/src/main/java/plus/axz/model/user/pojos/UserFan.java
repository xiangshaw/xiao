package plus.axz.model.user.pojos;

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
 * description APP用户粉丝表
 */
@Data
@TableName("user_fan")
public class UserFan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 粉丝ID
     */
    @TableField("fans_id")
    private Long fansId;

    /**
     * 粉丝昵称
     */
    @TableField("fans_name")
    private String fansName;

    /**
     * 粉丝忠实度
     * 0 正常
     * 1 潜力股
     * 2 勇士
     * 3 铁杆
     * 4 老铁
     */
    @TableField("level")
    private Short level;

    /**
     * 创建时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;

    /**
     * 是否可见我动态
     */
    @TableField("is_display")
    private Boolean isDisplay;

    /**
     * 是否屏蔽私信
     */
    @TableField("is_shield_letter")
    private Boolean isShieldLetter;

    /**
     * 是否屏蔽评论
     */
    @TableField("is_shield_comment")
    private Boolean isShieldComment;
}
