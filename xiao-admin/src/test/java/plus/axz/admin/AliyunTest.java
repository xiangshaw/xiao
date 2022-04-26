package plus.axz.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import plus.axz.common.aliyun.GreeTextScan;
import plus.axz.common.aliyun.GreenImageScan;
import plus.axz.common.fastdfs.FastDFSClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxiang
 * @date 2022年04月26日
 * @particulars 测试文本图片审核
 */

@SpringBootTest(classes = AdminApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {

    @Autowired
    private GreeTextScan greeTextScan;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Test
    public void testText() throws Exception {
        Map map = greeTextScan.greeTextScan("这是一个文本,买卖冰毒是违法的");
        System.out.println(map);
    }

    @Test
    public void test1() {
        String accessKeyId = greeTextScan.getAccessKeyId();
        String secret = greeTextScan.getSecret();
        System.out.println(accessKeyId + secret);
    }

    @Test
    public void testImage() throws Exception {
        byte[] image1 = fastDFSClient.download("group1", "M00/00/00/wKgU3WHSnRGAArwcAATFbH5Egbw924.png");
        List<byte[]> images = new ArrayList<>();
        images.add(image1);
        Map map = greenImageScan.imageScan(images);
        System.out.println(map);
    }
}
