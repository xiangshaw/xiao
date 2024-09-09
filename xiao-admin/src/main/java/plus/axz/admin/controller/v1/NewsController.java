package plus.axz.admin.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plus.axz.admin.service.NewsService;
import plus.axz.api.admin.NewsControllerApi;
import plus.axz.model.admin.dtos.NewsDto;
import plus.axz.model.admin.pojos.News;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * description admin端文章测试
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/news")
public class NewsController implements NewsControllerApi {

    private final NewsService newsService;

    @Override
    @PostMapping("/save")
    public ResponseResult<?> save(@RequestBody News news) {
        news.setCreatedTime(new Date());
        return newsService.insert(news);
    }

    @Override
    @PostMapping("/update")
    public ResponseResult<?> update(@RequestBody News news) {
        news.setSubmitedTime(new Date());
        return newsService.update(news);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult<?> deleteById(@PathVariable("id") Integer id) {
        return newsService.deleteById(id);
    }

    @Override
    @PostMapping("/list")
    public ResponseResult<?> findAll() {
        List<News> list = newsService.list();
        return ResponseResult.okResult(list);
    }

    @Override
    @PostMapping("/findList")
    public ResponseResult<?> findByNameAndPage(@RequestBody NewsDto dto) {
        return newsService.findByNameAndPage(dto);
    }

    @Override
    @GetMapping("/findNewName/{search}")
    public ResponseResult<?> findNewName(@PathVariable("search") String search) {
        List<News> list = newsService.list(Wrappers.<News>lambdaQuery().eq(News::getTitle, search));
        // 不等于null且不等于空
        if (list != null && !list.isEmpty()) {
            return ResponseResult.okResult(list.get(0));
        }
        return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "搜索失败");
    }
}
