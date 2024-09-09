package plus.axz.comment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import plus.axz.model.admin.pojos.Sensitive;

import java.util.List;

/**
 * @author xiaoxiang
 * description 敏感词feign接口
 */
@FeignClient("xiao-admin")
public interface SensitiveFeign {
    @GetMapping("/api/v1/sensitive/all")
    List<Sensitive> findAllSensitive();
}
