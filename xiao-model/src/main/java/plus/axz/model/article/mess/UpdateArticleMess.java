package plus.axz.model.article.mess;

import lombok.Data;

/**
 * @author xiaoxiang
 * description 修改文章的消息体
 */
@Data
public class UpdateArticleMess {
    /**
     * 修改文章的字段类型
     */
    private UpdateArticleType type;
    /**
     * 文章ID
     */
    private Long articleId;
    /**
     * 修改数据的增量，可为正负
     */
    private Integer add;

    public enum UpdateArticleType{
        COLLECTION,COMMENT,LIKES,VIEWS;
    }
}
