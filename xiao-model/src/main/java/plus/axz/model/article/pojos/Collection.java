package plus.axz.model.article.pojos;

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
 * description app收藏信息
 */
@Data
@TableName("collection")
public class Collection implements Serializable {
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
     * 点赞内容类型
     0文章
     1动态
     */
    @TableField("type")
    private Short type;

    /**
     * 0 收藏
     * 1 取消收藏
     */
    @TableField("operation")
    private Short operation;

    /**
     * 创建时间
     */
    @TableField("collection_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date collectionTime;

    /**
     * 发布时间
     */
    @TableField("published_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date publishedTime;

    // 定义收藏内容类型的枚举
    @Alias("CollectionEnumType")
    public enum Type{
        ARTICLE((short)0),DYNAMIC((short)1);
        short code;
        Type(short code){
            this.code = code;
        }
        public short getCode(){
            return this.code;
        }
    }

    public boolean isCollectionArticle(){
        return (this.getType()!=null&&this.getType().equals(Type.ARTICLE));
    }
}
