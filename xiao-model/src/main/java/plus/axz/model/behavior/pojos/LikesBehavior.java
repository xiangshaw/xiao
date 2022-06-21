package plus.axz.model.behavior.pojos;

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
 * @date 2022年06月21日
 * @particulars APP点赞行为表
 */
@Data
@TableName("likes_behavior")
public class LikesBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @IdEncrypt
    private Long id;

    /**
     * 实体ID
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
     * 点赞内容类型
     * 0文章
     * 1动态
     */
    @TableField("type")
    private Short type;

    /**
     * 0 点赞
     * 1 取消点赞
     */
    @TableField("operation")
    private Short operation;

    /**
     * 登录时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;

    // 定义点赞内容的类型
    @Alias("LikesBehaviorEnumType")
    public enum Type {
        ARTICLE((short) 0), DYNAMIC((short) 1), COMMENT((short) 2);
        short code;

        Type(short code) {
            this.code = code;
        }

        public short getCode() {
            return this.code;
        }
    }

    //定义点赞操作的方式，点赞还是取消点赞
    @Alias("LikesBehaviorEnumOperation")
    public enum Operation {
        LIKE((short) 0), CANCEL((short) 1);
        short code;

        Operation(short code) {
            this.code = code;
        }

        public short getCode() {
            return this.code;
        }
    }
}
