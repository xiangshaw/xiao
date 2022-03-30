package plus.axz.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars 在实名认证是否通过，通过就 创建自媒体账户 并 创建作者
 */
//服务名称找nacos注册的那个
@FeignClient("xiao-article")
public interface ArticleFeign {

    @GetMapping("/api/v1/author/findByUserId/{id}")
    public Author findByUserId(@PathVariable("id") Integer id);

    @PostMapping("/api/v1/author/save")
    public ResponseResult save(@RequestBody Author author);

}
