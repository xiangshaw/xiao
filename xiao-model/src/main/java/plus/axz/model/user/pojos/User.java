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
 * @date 2022年03月25日
 * @particulars 用户信息表
 */
@Data
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 密码、通信等加密盐
     */
    @TableField("salt")
    private String salt;
    /**
     * 用户名
     */
    @TableField("name")
    private String name;
    /**
     * 密码,md5加密
     */
    @TableField("password")
    private String password;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 头像
     */
    @TableField("image")
    private String image;
    /**
     * 0男
     * 1女
     * 2 未知
     */
    @TableField("sex")
    private Boolean sex;
    /**
     * 0未
     * 1是
     */
    @TableField("is_certification")
    private Boolean certification;
    /**
     * 是否身份认证
     */
    @TableField("is_identity_authentication")
    private Boolean identityAuthentication;
    /**
     * 0正常
     * 1锁定
     */
    @TableField("status")
    private Boolean status;
    /**
     * 0 普通用户
     * 1 自媒体人
     * 2 大V
     */
    @TableField("flag")
    private Short flag;
    /**
     * 注册时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;
}
