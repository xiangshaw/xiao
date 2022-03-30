package plus.axz.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2022年03月25日
 * @particulars
 */
@RestController
@RequestMapping("/api/v1/author")
public class AuthorServiceImpl implements AuthorControllerApi {
    @Autowired
    private AuthorService authorService;
    @Override
    @GetMapping("/findByUserId/{id}")
    public Author findByUserId(@PathVariable("id") Integer id) {
        List<Author> list = authorService.list(Wrappers.<Author>lambdaQuery().eq(Author::getId, id));
        if (list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody Author author) {
        author.setCreatedTime(new Date());
        authorService.save(author);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    @GetMapping("/findByName/{name}")
    public Author selectAuthorByName(@PathVariable("name") String name) {
        Author author = authorService.getOne(Wrappers.<Author>lambdaQuery().eq(Author::getName, name));
        return author;
    }

    @Override
    @GetMapping("/one/{id}")
    public Author findById(@PathVariable("id") Integer id) {
        return authorService.getById(id);
    }
}
