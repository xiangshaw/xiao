package plus.axz.admin.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import plus.axz.admin.feign.WemediaFeign;
import plus.axz.admin.service.WeMediaNewsAutoScanService;

import java.util.List;

/**
 * @author xiaoxiang
 * description 创建任务,查询自媒体文章后进行审核
 */
@RequiredArgsConstructor
@Log4j2
@Component
public class WeMediaNewsAutoScanJob {

    /*自动审核*/
    private final WeMediaNewsAutoScanService weMediaNewsAutoScanService;

    private final WemediaFeign wemediaFeign;

    /**
     * 每天0点执行一次
     */
    @XxlJob("wemediaAutoScanJob")
    public ReturnT<String> hello(String param) throws Exception {
        log.info("自媒体文章审核调度任务开始执行....");
        //1.查询符合条件的文章
        List<Integer> releaseIdList = wemediaFeign.findRelease();
        //2.调用自动审核
        if(null!=releaseIdList && !releaseIdList.isEmpty()){
            for (Integer id : releaseIdList) {
                weMediaNewsAutoScanService.autoScanByMediaNewsId(id);
            }
        }
        log.info("自媒体文章审核调度任务执行结束....");
        return ReturnT.SUCCESS;
    }
}
