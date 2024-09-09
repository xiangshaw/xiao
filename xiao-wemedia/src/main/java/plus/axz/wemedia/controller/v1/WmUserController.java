package plus.axz.wemedia.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plus.axz.api.wemedia.WmUserControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.wemedia.pojos.WmUser;
import plus.axz.wemedia.service.WmUserService;

import java.util.List;

/**
 * @author xiaoxiang
 * description
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class WmUserController implements WmUserControllerApi {

    private final WmUserService wmUserService;

    @Override
    @PostMapping("/save")
    public ResponseResult<?> save(@RequestBody WmUser wmUser) {
        wmUserService.save(wmUser);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    @GetMapping("/findByName/{name}")
    public WmUser findByName(@PathVariable("name") String name) {
        List<WmUser> list = wmUserService.list(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, name));
        // 不为null且不等于空
        if (list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    @GetMapping("/findOne/{id}")
    public WmUser findWmUserById(@PathVariable("id") Long id) {
        return wmUserService.getById(id);
    }
}
