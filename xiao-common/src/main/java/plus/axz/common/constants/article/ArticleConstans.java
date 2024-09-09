package plus.axz.common.constants.article;

/**
 * @author xiaoxiang
 * description 文章常量类
 */
public class ArticleConstans {

    private ArticleConstans() {
        throw new IllegalStateException("Utility class");
    }

    public static final Short LOADTYPE_LOAD_MORE = 1;
    public static final Short LOADTYPE_LOAD_NEW = 2;
    public static final String DEFAULT_TAG = "__all__";

    /**
     * 分值权重
     */
    public static final Integer HOT_ARTICLE_LIKE_WEIGHT = 3;
    public static final Integer HOT_ARTICLE_COMMENT_WEIGHT = 5;
    public static final Integer HOT_ARTICLE_COLLECTION_WEIGHT = 8;

    public static final String HOT_ARTICLE_FIRST_PAGE = "hot_article_first_page_";
}
