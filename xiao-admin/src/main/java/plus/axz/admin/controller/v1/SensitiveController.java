package plus.axz.admin.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plus.axz.admin.service.SensitiveService;
import plus.axz.api.admin.SensitiveControllerApi;
import plus.axz.model.admin.dtos.SensitiveDto;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.common.dtos.ResponseResult;

import java.util.List;

/**
 * @author xiaoxiang
 * description 敏感词控制层
 */
@RequiredArgsConstructor
@RestController /*这有两个注解的意思：1(Controller)加到spingboot容器管理,2(ResponseBody)将方法的返回值转成json并响应回去*/
@RequestMapping("/api/v1/sensitive")
public class SensitiveController implements SensitiveControllerApi {

    private final SensitiveService sensitiveService;

    @Override
    @PostMapping("/list")
    public ResponseResult<?> list(@RequestBody SensitiveDto dto) {
        return sensitiveService.list(dto);
    }

    @Override
    @PostMapping("/insert")
    public ResponseResult<?> insert(@RequestBody Sensitive sensitives) {
        return sensitiveService.insert(sensitives);
    }

    @Override
    @PostMapping("/update")
    public ResponseResult<?> update(@RequestBody Sensitive sensitives) {
        return sensitiveService.update(sensitives);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult<?> deleteById(@PathVariable("id") Integer id) {
        return sensitiveService.deleteById(id);
    }

    @Override
    @GetMapping("/all")
    public List<Sensitive> findAllSensitive() {
        return  sensitiveService.list();
    }
}
