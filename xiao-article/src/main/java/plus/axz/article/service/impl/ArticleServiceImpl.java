package plus.axz.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import plus.axz.article.mapper.ArticleMapper;
import plus.axz.article.service.ArticleService;
import plus.axz.common.constants.article.ArticleConstans;
import plus.axz.model.article.dtos.ArticleHomeDto;
import plus.axz.model.article.pojos.Article;
import plus.axz.model.common.dtos.ResponseResult;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年05月03日
 * @particulars 文章信息 -- 首页加载
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    // 单页最大加载的数字
    private final static short MAX_PAGE_SIZE = 50;
    @Autowired
    private ArticleMapper articleMapper;
    @Value("${fdfs.url}")
    private String fileServerUrl;

    @Override
    public ResponseResult load(ArticleHomeDto dto, Short loadType) {
        //1.检验参数
        Integer size = dto.getSize();
        if (size == null || size == 0) {
            size = 10;
        }
        size = Math.min(size, MAX_PAGE_SIZE);/*size小于50的话就取size,大于50则取50这个值，请求数据不超过50条*/
        dto.setSize(size);/*设置分页的条数*/
        //类型参数校验
        if (!loadType.equals(ArticleConstans.LOADTYPE_LOAD_MORE) && !loadType.equals(ArticleConstans.LOADTYPE_LOAD_NEW)) {
            loadType = ArticleConstans.LOADTYPE_LOAD_MORE;/*加载更多*/
        }
        //文章频道校验
        if (StringUtils.isEmpty(dto.getTag())) {
            dto.setTag(ArticleConstans.DEFAULT_TAG);/*tag为空，给 __all__ 值*/
        }
        //时间校验
        if (dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());/*为空，给个当前时间*/
        if (dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());
        //2.查询数据
        List<Article> apArticles = articleMapper.loadArticleList(dto, loadType);
        //3.结果封装
        ResponseResult responseResult = ResponseResult.okResult(apArticles);
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }
}
