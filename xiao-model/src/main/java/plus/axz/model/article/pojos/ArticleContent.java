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
 * @particulars 文章内容表
 */
@Data
@TableName("article_content")
public class ArticleContent {
    @TableId(value = "id", type = IdType.ASSIGN_ID)/*雪花算法*/
    @IdEncrypt
    private Long id;
    /**
     * 文章id
     */
    @TableField("article_id")
    @IdEncrypt
    private Long articleId;
    /**
     * 文章内容
     */
    private String content;
}
