package plus.axz.behavior.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.behavior.BehaviorEntryControllerApi;
import plus.axz.behavior.service.BehaviorEntryService;
import plus.axz.model.behavior.pojos.BehaviorEntry;

/**
 * @author xiaoxiang
 * description 行为实体控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/behavior_entry")
public class BehaviorEntryController implements BehaviorEntryControllerApi {

    private final BehaviorEntryService behaviorEntryService;

    @GetMapping("/one")
    @Override
    // 根据行为实体id和文章id查询点赞行为
    public BehaviorEntry findByUserIdOrEquipmentId(@RequestParam("userId") Integer userId, @RequestParam("equipmentId") Integer equipmentId) {
        return behaviorEntryService.findByUserIdOrEquipmentId(userId,equipmentId);
    }
}
