package plus.axz.article.service;

import plus.axz.model.article.dtos.ArticleInfoDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 加载文章详情
 */
public interface ArticleInfoService {

    ResponseResult<?> loadArticleInfo(ArticleInfoDto dto);

    //  加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢等
    ResponseResult<?> loadArticleBehavior(ArticleInfoDto dto);
}
