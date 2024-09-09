package plus.axz.api.article;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.article.dtos.ArticleInfoDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description app端 加载文章详情
 */
public interface ArticleInfoControllerApi {

    @ApiOperation("加载文章详情")
    ResponseResult<?> loadArticleInfo(ArticleInfoDto dto);

    @ApiOperation("加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢等")
    ResponseResult<?> loadArticleBehavior(ArticleInfoDto dto);
}
