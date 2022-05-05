package plus.axz.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.axz.article.mapper.ArticleConfigMapper;
import plus.axz.article.service.ArticleConfigService;
import plus.axz.model.article.pojos.ArticleConfig;

/**
 * @author xiaoxiang
 * @date 2022年05月03日
 * @particulars 文章配置
 */
@Service
public class ArticleConfigServiceImpl extends ServiceImpl<ArticleConfigMapper, ArticleConfig> implements ArticleConfigService {
}
