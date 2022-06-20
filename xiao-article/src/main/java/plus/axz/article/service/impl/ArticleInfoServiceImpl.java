package plus.axz.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.article.mapper.ArticleConfigMapper;
import plus.axz.article.mapper.ArticleContentMapper;
import plus.axz.article.service.ArticleInfoService;
import plus.axz.model.article.dtos.ArticleInfoDto;
import plus.axz.model.article.pojos.ArticleConfig;
import plus.axz.model.article.pojos.ArticleContent;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.HashMap;

/**
 * @author xiaoxiang
 * @date 2022年06月17日
 * @particulars 文章详情
 */
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Autowired
    private ArticleConfigMapper articleConfigMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;

    @Override
    public ResponseResult loadArticleInfo(ArticleInfoDto dto) {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 1.检查参数
        if (dto == null || dto.getArticleId() == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.查询文章配置
        ArticleConfig articleConfig = articleConfigMapper.selectOne(Wrappers.<ArticleConfig>lambdaQuery().eq(ArticleConfig::getArticleId, dto.getArticleId()));
        if (articleConfig == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.查询文章内容--非下架非删除内容
        // 1上0下 1删0未删
        if (!articleConfig.getIsDelete() && articleConfig.getIsDown()) {
            ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, dto.getArticleId()));
            resultMap.put("content",articleContent);
        }
        resultMap.put("config",articleConfig);
        return ResponseResult.okResult(resultMap);
    }
}
