package plus.axz.api.article;

import io.swagger.annotations.ApiOperation;
import plus.axz.model.article.pojos.ArticleContent;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description admin端远程调用
在文章审核成功以后需要在app的article库中新增文章数据
 * 1 保存文章信息    article，需要返回当前文章，并且需要获取保存后获取到的主键
 * 2 保存文章配置信息    article_config
 * 3 保存文章内容    article_content
 * 4 在保存文章的时候需要关联作者，需要根据名称查询作者信息
 */
public interface ArticleContentControllerApi {
    @ApiOperation("保存文章内容")
    ResponseResult<?> saveArticleContent(ArticleContent articleContent);
}
