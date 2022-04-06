package plus.axz.wemedia.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.wemedia.WmNewsControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmNewsDto;
import plus.axz.model.wemedia.dtos.WmNewsPageReqDto;
import plus.axz.model.wemedia.pojos.WmNews;
import plus.axz.wemedia.service.WmNewsService;

/**
 * @author xiaoxiang
 * @date 2022年04月03日
 * @particulars
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController implements WmNewsControllerApi {
    @Autowired
    private WmNewsService wmNewsService;

    @PostMapping("/list")
    @Override
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto) {
        return wmNewsService.findAll(dto);
    }

    @Override
    @PostMapping("/submit")
    public ResponseResult saveNews(@RequestBody WmNewsDto dto) {
        if (dto.getStatus() == WmNews.Status.SUBMIT.getCode()){
            // 提交保存
            return wmNewsService.saveNews(dto,WmNews.Status.SUBMIT.getCode());
        }else {
            // 草稿
            return wmNewsService.saveNews(dto,WmNews.Status.NORMAL.getCode());
        }
    }
}
