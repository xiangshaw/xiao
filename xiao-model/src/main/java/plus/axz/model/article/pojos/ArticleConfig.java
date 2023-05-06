package plus.axz.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

/**
 * @author xiaoxiang
 * @date 2022年05月02日
 * @particulars APP已发布文章配置表
 */
@Data
@TableName("article_config")
public class ArticleConfig {
    @TableId(value = "id", type = IdType.ID_WORKER)/*雪花算法*/
    @IdEncrypt
    private Long id;
    /**
     * 文章id
     */
    @TableField("article_id")
    @IdEncrypt
    private Long articleId;
    /**
     * 是否可评论
     */
    @TableField("is_comment")
    private Boolean isComment;
    /**
     * 是否转发
     */
    @TableField("is_forward")
    private Boolean isForward;
    /**
     * 是否下架
     */
    @TableField("is_down")
    private Boolean isDown;
    /**
     * 是否已删除
     */
    @TableField("is_delete")
    private Boolean isDelete;
}
