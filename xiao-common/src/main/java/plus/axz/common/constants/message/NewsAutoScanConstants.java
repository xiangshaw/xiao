package plus.axz.common.constants.message;

/**
 * @author xiaoxiang
 * description 定义topic名称-生产者
 */
public class NewsAutoScanConstants {

    private NewsAutoScanConstants() {
        throw new IllegalStateException("Utility class");
    }

    // 发送消息
    public static final String WM_NEWS_AUTO_SCAN_TOPIC = "wm.news.auto.scan.topic";
}
