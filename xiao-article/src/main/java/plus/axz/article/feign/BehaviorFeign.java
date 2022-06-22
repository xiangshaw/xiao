package plus.axz.article.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.behavior.pojos.LikesBehavior;
import plus.axz.model.behavior.pojos.UnlikesBehavior;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 行为远程接口
 */
@FeignClient("xiao-behavior")
public interface BehaviorFeign {

    @GetMapping("/api/v1/behavior_entry/one")
    public BehaviorEntry findByUserIdOrEntryId(@RequestParam("userId") Integer userId, @RequestParam("equipmentId") Integer equipmentId);

    @GetMapping("/api/v1/unlikes_behavior/one")
    UnlikesBehavior findUnLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId);

    @GetMapping("/api/v1/likes_behavior/one")
    LikesBehavior findLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId, @RequestParam("type") short type);
}
