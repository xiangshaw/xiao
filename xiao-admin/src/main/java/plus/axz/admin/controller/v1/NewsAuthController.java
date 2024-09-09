package plus.axz.admin.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plus.axz.admin.service.WeMediaNewsAutoScanService;
import plus.axz.api.admin.NewsAuthControllerApi;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 新闻审核管理
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/news_auth")
public class NewsAuthController implements NewsAuthControllerApi {

    private final WeMediaNewsAutoScanService weMediaNewsAutoScanService;

    @PostMapping("/list")
    @Override
    public ResponseResult<?> findNews(@RequestBody NewsAuthDto dto) {
        return weMediaNewsAutoScanService.findNews(dto);
    }

    @GetMapping("/one/{id}")
    @Override
    public ResponseResult<?> findOne(@PathVariable("id") Integer id) {
        return weMediaNewsAutoScanService.findOne(id);
    }

    @PostMapping("/auth_pass")
    @Override
    public ResponseResult<?> authPass(@RequestBody NewsAuthDto dto) {
        return weMediaNewsAutoScanService.updateStatus(dto, 1);
    }

    @PostMapping("/auth_fail")
    @Override
    public ResponseResult<?> authFail(@RequestBody NewsAuthDto dto) {
        return weMediaNewsAutoScanService.updateStatus(dto, 0);
    }
}
