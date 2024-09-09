package plus.axz.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.model.article.dtos.ArticleHomeDto;
import plus.axz.model.article.mess.ArticleVisitStreamMess;
import plus.axz.model.article.pojos.Article;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 文章信息
 */
public interface ArticleService extends IService<Article> {

    /*ArticleHomeControllerApi虽有三个参数，但传入参数都一样，可通过时间来判断*/

    /**
     * 根据参数加载文章列表
     *
     * @param loadType 1加载更多 2加载更新（加载首页）
     */
    ResponseResult<?> load(ArticleHomeDto dto, Short loadType);

    /**
     * 重新计算文章分值
     */
    void updateArticle(ArticleVisitStreamMess mess);

    /**
     * 根据参数加载文章列表  v2
     */
    ResponseResult<?> load2(ArticleHomeDto dto, Short loadtypeLoadMore, boolean firstPage);
}
