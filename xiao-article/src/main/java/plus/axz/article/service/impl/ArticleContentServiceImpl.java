package plus.axz.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.axz.article.mapper.ArticleContentMapper;
import plus.axz.article.service.ArticleContentService;
import plus.axz.model.article.pojos.ArticleContent;

/**
 * @author xiaoxiang
 * description 文章内容
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper,ArticleContent> implements ArticleContentService {
}
