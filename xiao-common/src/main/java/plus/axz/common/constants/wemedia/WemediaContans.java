package plus.axz.common.constants.wemedia;

/**
 * @author xiaoxiang
 * description 自媒体常量
 */
public class WemediaContans {

    private WemediaContans() {
        throw new IllegalAccessError("Utility class");
    }

    /*收藏*/
    public static final Short COLLECT_MATERIAL = 1;
    /*取消收藏*/
    public static final Short CANCEL_COLLECT_MATERIAL = 0;
    /*单图*/
    public static final Short WM_NEWS_SINGLE_TYPE = 1;
    /*无图*/
    public static final Short WM_NEWS_NONE_TYPE = 0;
    /*多图*/
    public static final Short WM_NEWS_MANY_TYPE = 3;
    /*自动*/
    public static final Short WM_NEWS_AUTO_TYPE = -1;
    /*图片*/
    public static final String WM_NEWS_TYPE_IMAGE = "image";
    /*图片内容引用*/
    public static final Short WM_NEWS_CONTENT_REFERENCE = 0;
    /*图片封面引用*/
    public static final Short WM_NEWS_COVER_REFERENCE = 1;
    /*上架*/
    public static final Short WM_NEWS_ENABLE_UP = 1;
}
