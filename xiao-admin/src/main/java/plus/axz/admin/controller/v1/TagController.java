package plus.axz.admin.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plus.axz.admin.service.TagService;
import plus.axz.api.admin.TagControllerApi;
import plus.axz.model.admin.dtos.TagDto;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.List;

/**
 * @author xiaoxiang
 * description 标签类别控制器
 */
/*构造器注入*/
@RequiredArgsConstructor
/*两个注解的意思：1(Controller)加到spingboot容器管理,2(ResponseBody)将方法的返回值转成json并响应回去*/
@RestController
/*请求映射*/
@RequestMapping("/api/v1/tag")
public class TagController implements TagControllerApi {

    private final TagService tagService;

    @PostMapping("/list")
    @Override
    /*接收json数据并转成TagDto需要加RequestBody 注解*/
    public ResponseResult<?> findByNameAndPage(@RequestBody TagDto dto) {
        return tagService.findByNameAndPage(dto);
    }

    @Override
    @PostMapping("/save")
    public ResponseResult<?> save(@RequestBody Tag tag) {
        return tagService.insert(tag);
    }

    @Override
    @PostMapping("/update")
    public ResponseResult<?> update(@RequestBody Tag tag) {
        return tagService.update(tag);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult<?> deleteById(@PathVariable("id") Integer id) {
        return tagService.deleteById(id);
    }

    @GetMapping("/tags")
    @Override
    public ResponseResult<?> findAll() {
        List<Tag> list = tagService.list();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/findTagName/{search}")
    @Override
    public ResponseResult<?> findTagName(@PathVariable("search") String search) {
        List<Tag> list = tagService.list(Wrappers.<Tag>lambdaQuery().eq(Tag::getTagName, search));
        // 不等于null且不等于空
        if (list != null && !list.isEmpty()) {
            return ResponseResult.okResult(list.get(0));
        }
        return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"搜索失败");
    }
}
