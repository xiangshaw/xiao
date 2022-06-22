package plus.axz.behavior.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.api.behavior.LikesBehaviorControllerApi;
import plus.axz.behavior.service.LikesBehaviorService;
import plus.axz.model.behavior.dtos.LikesBehaviorDto;
import plus.axz.model.behavior.pojos.LikesBehavior;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月21日
 * @particulars
 */
@RestController
@RequestMapping("/api/v1/likes_behavior")
public class LikesBehaviorController implements LikesBehaviorControllerApi {
    @Autowired
    private LikesBehaviorService likesBehaviorService;
    // 点赞行为
    @Override
    @PostMapping
    public ResponseResult like(@RequestBody LikesBehaviorDto dto) {
        return likesBehaviorService.like(dto);
    }

    // 点赞远程接口
    @GetMapping("/one")
    @Override
    public LikesBehavior findLikeByArticleIdAndEntryId(@RequestParam("articleId") Long articleId, @RequestParam("entryId") Integer entryId, @RequestParam("type") Short type) {
        LikesBehavior apLikesBehavior = likesBehaviorService.getOne(Wrappers.<LikesBehavior>lambdaQuery()
                .eq(LikesBehavior::getArticleId, articleId).eq(LikesBehavior::getEntryId, entryId)
                .eq(LikesBehavior::getType, type));
        return apLikesBehavior;
    }
}
