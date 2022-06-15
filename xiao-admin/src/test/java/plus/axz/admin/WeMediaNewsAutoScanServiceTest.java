package plus.axz.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import plus.axz.admin.service.WeMediaNewsAutoScanService;

/**
 * @author xiaoxiang
 * @date 2022年05月05日
 * 审核测试
 */
@SpringBootTest(classes = AdminApplication.class)
@RunWith(SpringRunner.class)
public class WeMediaNewsAutoScanServiceTest {

    @Autowired
    WeMediaNewsAutoScanService weMediaNewsAutoScanService;

    // 需要启动 "文章微服务" 和 "自媒体微服务"
    // 填写需要审核的文章id进行测试
    @Test
    public void testScanNews(){
        weMediaNewsAutoScanService.autoScanByMediaNewsId(43);
    }
}
