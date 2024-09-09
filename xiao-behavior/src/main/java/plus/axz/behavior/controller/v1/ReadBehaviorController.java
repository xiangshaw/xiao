package plus.axz.behavior.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.behavior.ReadBehaviorControllerApi;
import plus.axz.behavior.service.ReadBehaviorService;
import plus.axz.model.behavior.dtos.ReadBehaviorDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 阅读行为
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/read_behavior")
public class ReadBehaviorController implements ReadBehaviorControllerApi {

    private final ReadBehaviorService readBehaviorService;

    @PostMapping
    @Override
    public ResponseResult<?> readBehavior(@RequestBody ReadBehaviorDto dto) {
        return readBehaviorService.readBehavior(dto);
    }
}
