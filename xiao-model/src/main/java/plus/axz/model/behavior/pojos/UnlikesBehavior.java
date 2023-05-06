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
 * @particulars 不喜欢行为表
 */
@Data
@TableName("unlikes_behavior")
public class UnlikesBehavior implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
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
     * 0 不喜欢
     1 取消不喜欢
     */
    @TableField("type")
    private Short type;

    /**
     * 登录时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;
    // 定义不喜欢操作的类型
    @Alias("UnlikesBehaviorEnumType")
    public enum Type{
        UNLIKE((short)0),CANCEL((short)1);
        short code;
        Type(short code){
            this.code = code;
        }
        public short getCode(){
            return this.code;
        }
    }
}
