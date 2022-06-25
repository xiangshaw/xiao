package plus.axz.article.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import plus.axz.article.ArticleApplication;
import plus.axz.article.service.HotArticlesService;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars 测试查询热点文章
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class HotArticleServiceTest {

    @Autowired
    private HotArticlesService hotArticlesService;

    @Test
    public void tCHA(){
        hotArticlesService.computeHotArticle();
    }/*testComputeHotArticle*/
}
