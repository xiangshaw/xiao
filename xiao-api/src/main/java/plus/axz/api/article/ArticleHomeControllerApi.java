package plus.axz.api.article;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.article.dtos.ArticleHomeDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 首页文章展示
 */
public interface ArticleHomeControllerApi {

    @ApiOperation("加载首页文章")
     ResponseResult<?> load(ArticleHomeDto dto);

    @ApiOperation("加载更多")
     ResponseResult<?> loadMore(ArticleHomeDto dto);

    @ApiOperation("加载最新")
     ResponseResult<?> loadNew(ArticleHomeDto dto);
}
