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
 * description 认证成功后的自媒体用户
 */
@Data
@TableName("wm_user")
public class WmUser implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("author_id")
    private Integer authorId;
    /**
     * 登录用户名
     */
    @TableField("name")
    private String name;
    /**
     * 登录密码
     */
    @TableField("password")
    private String password;
    /**
     * 盐
     */
    @TableField("salt")
    private String salt;
    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;
    /**
     * 头像
     */
    @TableField("image")
    private String image;
    /**
     * 归属地
     */
    @TableField("location")
    private String location;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 状态
     * 0 暂时不可用
     * 1 永久不可用
     * 9 正常可用
     */
    @TableField("status")
    private Integer status;
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 账号类型
     * 0 个人
     * 1 企业
     * 2 子账号
     */
    @TableField("type")
    private Integer type;
    /**
     * 运营评分
     */
    @TableField("score")
    private Integer score;
    /**
     * 最后一次登录时间
     */
    @TableField("login_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date loginTime;
    /**
     * 创建时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;
}
