package plus.axz.article.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars 计算完成新热数据后，需要给每个频道缓存一份数据，所以需要查询所有频道信息
 */
@FeignClient("xiao-admin")
public interface AdminFeign {
    @GetMapping("/api/v1/tag/tags")
    ResponseResult selectAllTag();
}
