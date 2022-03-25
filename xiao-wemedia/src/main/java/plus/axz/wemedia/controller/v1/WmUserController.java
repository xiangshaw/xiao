package plus.axz.wemedia.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.api.wemedia.WmUserControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.wemedia.WmUser;
import plus.axz.wemedia.service.WmUserService;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars
 */
@RestController
@RequestMapping("/api/v1/user")
public class WmUserController implements WmUserControllerApi {
    @Autowired
    private WmUserService wmUserService;

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody WmUser wmUser) {
        wmUserService.save(wmUser);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    @GetMapping("/findByName/{name}")
    public WmUser findByName(@PathVariable("name") String name) {
        List<WmUser> list = wmUserService.list(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, name));
        if (list != null && !list.isEmpty()){ // 不为null且不等于空
            return list.get(0);
        }
        return null;
    }
}
