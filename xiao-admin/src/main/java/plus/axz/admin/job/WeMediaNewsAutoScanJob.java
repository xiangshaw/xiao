package plus.axz.admin.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plus.axz.admin.feign.WemediaFeign;
import plus.axz.admin.service.WeMediaNewsAutoScanService;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年06月15日
 * @particulars 创建任务,查询自媒体文章后进行审核
 */
@Component
@Log4j2
public class WeMediaNewsAutoScanJob {
    @Autowired
    private WeMediaNewsAutoScanService weMediaNewsAutoScanService;/*自动审核*/

    @Autowired
    private WemediaFeign wemediaFeign;

    /**
     * 每天0点执行一次
     * @param param
     * @return
     * @throws Exception
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
