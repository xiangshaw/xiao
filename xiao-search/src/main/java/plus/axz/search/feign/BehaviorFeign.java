package plus.axz.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plus.axz.model.behavior.pojos.BehaviorEntry;

/**
 * @author xiaoxiang
 * description 在查询用户搜索记录的时候需要根据行为实体查询
 */
@FeignClient("xiao-behavior")
public interface BehaviorFeign {

    @GetMapping("/api/v1/behavior_entry/one")
    BehaviorEntry findByUserIdOrEntryId(@RequestParam("userId") Integer userId, @RequestParam("equipmentId") Integer equipmentId);

}
