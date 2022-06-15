package plus.axz.wemedia.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.api.wemedia.WmNewsControllerApi;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.wemedia.dtos.WmNewsDto;
import plus.axz.model.wemedia.dtos.WmNewsPageReqDto;
import plus.axz.model.wemedia.pojos.WmNews;
import plus.axz.model.wemedia.vo.WmNewsVo;
import plus.axz.wemedia.service.WmNewsService;

import java.util.List;

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

    @Override
    @GetMapping("/one/{id}")
    public ResponseResult findWmNewById(@PathVariable("id") Integer id) {
        return wmNewsService.findWmNewById(id);
    }

    @Override
    @GetMapping("/del_news/{id}")
    public ResponseResult delNews(@PathVariable("id") Integer id) {
        return wmNewsService.delNews(id);
    }

    @Override
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto) {
        return wmNewsService.downOrUp(dto);
    }
    //================以下为ADMIN远程调用========================
    @Override
    @GetMapping("/findOne/{id}")
    public WmNews findById(@PathVariable("id") Integer id) {
        return wmNewsService.getById(id);
    }

    @Override
    @PostMapping("/update")
    public ResponseResult updateWmNews(@RequestBody WmNews wmNews) {
        boolean b = wmNewsService.updateById(wmNews);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    /*admin远程调用，查看状态为8的id*/
    @GetMapping("/findRelease")
    @Override
    public List<Integer> findRelease() {
        return wmNewsService.findRelease();
    }

    @PostMapping("/findList")
    @Override
    public PageResponseResult findList(@RequestBody NewsAuthDto dto) {
        return wmNewsService.findList(dto);
    }

    @GetMapping("/find_news_vo/{id}")
    @Override
    public WmNewsVo findWmNewsVo(@PathVariable("id") Integer id) {
        return wmNewsService.findWmNewsVo(id);
    }
}
