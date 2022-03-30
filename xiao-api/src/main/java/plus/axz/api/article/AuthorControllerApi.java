package plus.axz.api.article;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars
 */
public interface AuthorControllerApi {
    @ApiOperation("根据用户id查询作者")
    Author findByUserId(@PathVariable("id") Integer id);

    @ApiOperation("保存作者")
    ResponseResult save(Author author);

    @ApiOperation("根据名称查询作者")
    Author selectAuthorByName(String name);

    @ApiOperation("根据id查询作者")
    Author findById(@PathVariable("id") Integer id);
}
