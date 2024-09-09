package plus.axz.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.axz.article.feign.BehaviorFeign;
import plus.axz.article.feign.UserFeign;
import plus.axz.article.mapper.ArticleConfigMapper;
import plus.axz.article.mapper.ArticleContentMapper;
import plus.axz.article.mapper.AuthorMapper;
import plus.axz.article.mapper.CollectionMapper;
import plus.axz.article.service.ArticleInfoService;
import plus.axz.model.article.dtos.ArticleInfoDto;
import plus.axz.model.article.pojos.ArticleConfig;
import plus.axz.model.article.pojos.ArticleContent;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.article.pojos.Collection;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.behavior.pojos.LikesBehavior;
import plus.axz.model.behavior.pojos.UnlikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.pojos.User;
import plus.axz.model.user.pojos.UserFollow;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxiang
 * description 文章详情
 */
@RequiredArgsConstructor
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    private final ArticleConfigMapper articleConfigMapper;

    private final ArticleContentMapper articleContentMapper;

    @Override
    public ResponseResult<?> loadArticleInfo(ArticleInfoDto dto) {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 1.检查参数
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.查询文章配置
        ArticleConfig articleConfig = articleConfigMapper.selectOne(Wrappers.<ArticleConfig>lambdaQuery().eq(ArticleConfig::getArticleId, dto.getArticleId()));
        if (articleConfig == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.查询文章内容--非下架非删除内容
        // 1上0下 1删0未删
        if (!articleConfig.getIsDelete() && articleConfig.getIsDown()) {
            ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, dto.getArticleId()));
            resultMap.put("content", articleContent);
        }
        resultMap.put("config", articleConfig);
        return ResponseResult.okResult(resultMap);
    }

    // 行为展示业务
    private final BehaviorFeign behaviorFeign;

    private final CollectionMapper collectionMapper;

    private final UserFeign userFeign;

    private final AuthorMapper authorMapper;

    @Override
    public ResponseResult<?> loadArticleBehavior(ArticleInfoDto dto) {
        //1.检查参数
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        //2.查询行为实体
        User user = AppThreadLocalUtils.getUser();
        BehaviorEntry behaviorEntry = behaviorFeign.findByUserIdOrEntryId(user.getId(), dto.getEquipmentId());
        if (behaviorEntry == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }

        boolean isUnlike = false, isLike = false, isCollection = false, isFollow = false;

        //3.查询不喜欢行为
        UnlikesBehavior apUnlikesBehavior = behaviorFeign.findUnLikeByArticleIdAndEntryId(behaviorEntry.getId(), dto.getArticleId());
        if (apUnlikesBehavior != null && apUnlikesBehavior.getType() == UnlikesBehavior.Type.UNLIKE.getCode()) {
            isUnlike = true;
        }

        //4.查询点赞行为
        LikesBehavior apLikesBehavior = behaviorFeign.findLikeByArticleIdAndEntryId(behaviorEntry.getId(), dto.getArticleId(), LikesBehavior.Type.ARTICLE.getCode());
        if (apLikesBehavior != null && apLikesBehavior.getOperation() == LikesBehavior.Operation.LIKE.getCode()) {
            isLike = true;
        }

        //5.查询收藏行为
        Collection apCollection = collectionMapper.selectOne(Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getEntryId, behaviorEntry.getId())
                .eq(Collection::getArticleId, dto.getArticleId())
                .eq(Collection::getType, Collection.Type.ARTICLE.getCode()));
        if (apCollection != null) {
            isCollection = true;
        }

        //6.查询是否关注
        // 查出作者
        Author apAuthor = authorMapper.selectById(dto.getAuthorId());
        if (apAuthor != null) {
            UserFollow apUserFollow = userFeign.findByUserIdAndFollowId(user.getId(), apAuthor.getUserId());
            if (apUserFollow != null) {
                isFollow = true;
            }
        }

        // 7.结果返回  {"isfollow":true,"islike":true,"isunlike":false,"iscollection":true}
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isfollow", isFollow);
        resultMap.put("islike", isLike);
        resultMap.put("isunlike", isUnlike);
        resultMap.put("iscollection", isCollection);
        return ResponseResult.okResult(resultMap);
    }
}
