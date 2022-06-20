package plus.axz.api.article;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.article.dtos.ArticleInfoDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月17日
 * @particulars  app端 加载文章详情
 */
public interface ArticleInfoControllerApi {
    /**
     * 加载文章详情
     * @param dto
     * @return
     */
    @ApiOperation("加载文章详情")
    public ResponseResult loadArticleInfo(ArticleInfoDto dto);
}
