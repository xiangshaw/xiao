package plus.axz.article.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plus.axz.api.article.AuthorControllerApi;
import plus.axz.article.service.AuthorService;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * description 作者接口实现类
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/author")
public class AuthorController implements AuthorControllerApi {

    private final AuthorService authorService;

    @Override
    @GetMapping("/findByUserId/{id}")
    public Author findByUserId(@PathVariable("id") Integer id) {
        List<Author> list = authorService.list(Wrappers.<Author>lambdaQuery().eq(Author::getId, id));
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    @PostMapping("/save")
    public ResponseResult<?> save(@RequestBody Author author) {
        author.setCreatedTime(new Date());
        authorService.save(author);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    @GetMapping("/findByName/{name}")
    public Author selectAuthorByName(@PathVariable("name") String name) {
        return authorService.getOne(Wrappers.<Author>lambdaQuery().eq(Author::getName, name));
    }

    @Override
    @GetMapping("/one/{id}")
    public Author findById(@PathVariable("id") Integer id) {
        return authorService.getById(id);
    }
}
