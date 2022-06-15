package plus.axz.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.pojos.WmNews;
import plus.axz.model.wemedia.pojos.WmUser;
import plus.axz.model.wemedia.vo.WmNewsVo;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年05月02日
 * @particulars
 * 1 根据文章id查询自媒体文章的数据
 * 2 在审核的过程中,审核失败或者成功需要修改自媒体文章的状态
 * 3 在文章进行保存的时候需要查询作者信息，需要通过自媒体用户关联查询作者信息
 */
@FeignClient("xiao-wemedia")
public interface WemediaFeign {
    @GetMapping("/api/v1/news/findOne/{id}")
    WmNews findById(@PathVariable("id") Integer id);/*查自媒体文章*/
    @PostMapping("/api/v1/news/update")
    ResponseResult updateWmNews(WmNews wmNews);/*修改文章*/
    @GetMapping("/api/v1/user/findOne/{id}")
    WmUser findWmUserById(@PathVariable("id") Integer id);/*查询用户*/
    @GetMapping("/api/v1/news/findRelease")/*查看状态为8的id*/
    public List<Integer> findRelease();
    /**
     * 查看文章列表和查询文章详情，其中审核成功或失败可以调用之前定义好的修改方法updateWmNews()
     * 在查询列表的方法需要通过远程接口进行分页查看，返回值是PageResponseResult对象
     */
    @PostMapping("/api/v1/news/findList/")
    public PageResponseResult findList(NewsAuthDto dto);
    @GetMapping("/api/v1/news/find_news_vo/{id}")
    public WmNewsVo findWmNewsVo(@PathVariable("id") Integer id);
}
