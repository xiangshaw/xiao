package plus.axz.model.wemedia.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import plus.axz.model.common.annotation.IdEncrypt;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxiang
 * description 自媒体图文内容信息表
 */
@Data
@TableName("news")
public class WmNews implements Serializable {
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
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 图文内容
     */
    @TableField("content")
    private String content;
    /**
     * 文章布局
     0 无图文章
     1 单图文章
     3 多图文章
     */
    @TableField("type")
    private Short type;
    /**
     * 图文频道ID,分类
     */
    @TableField("tagId")
    private Integer tagId;
    @TableField("labels")
    private String labels;
    /**
     * 创建时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;
    /**
     * 提交时间
     */
    @TableField("submited_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date submitedTime;
    /**
     * 当前状态
     0 草稿
     1 提交（待审核）
     2 审核失败
     3 人工审核
     4 人工审核通过
     8 审核通过（待发布）
     9 已发布
     */
    @TableField("status")
    private Short status;
    /**
     * 定时发布时间，不定时则为空
     */
    @TableField("publish_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date publishTime;
    /**
     * 拒绝理由
     */
    @TableField("reason")
    private String reason;
    /**
     * 发布库文章ID
     */
    @TableField("article_id")
    @IdEncrypt
    private Long articleId;
    /**
     * //图片用逗号分隔
     */
    @TableField("images")
    private String images;
    @TableField("enable")
    private Short enable;
    //状态枚举类
    @Alias("WmNewsStatus")
    public enum Status{
        NORMAL((short)0),SUBMIT((short)1),FAIL((short)2),ADMIN_AUTH((short)3),ADMIN_SUCCESS((short)4),SUCCESS((short)
                8),PUBLISHED((short)9);
        short code;
        Status(short code){
            this.code = code;
        }
        public short getCode(){
            return this.code;
        }
    }
}
