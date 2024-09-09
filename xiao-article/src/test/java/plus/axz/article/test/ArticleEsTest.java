package plus.axz.article.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import plus.axz.article.ArticleApplication;
import plus.axz.article.mapper.ArticleContentMapper;
import plus.axz.article.service.ArticleService;
import plus.axz.model.article.pojos.Article;
import plus.axz.model.article.pojos.ArticleContent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxiang
 * description 文章ES测试
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class ArticleEsTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ArticleContentMapper articleContentMapper;

    @Test
    public void testImportAll() throws IOException {
        // 查询所有的文章
        List<Article> list = articleService.list();
        for (Article article : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", article.getId().toString());
            map.put("publishTime", article.getPublishTime());
            map.put("layout", article.getLayout());
            map.put("images", article.getImages());
            map.put("authorId", article.getAuthorId());
            map.put("title", article.getTitle());
            ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, article.getId()));
            if (articleContent != null) {
                map.put("content", articleContent.getContent());
            }
            IndexRequest indexRequest = new IndexRequest("article_info").id(article.getId().toString()).source(map);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        }
    }
}
