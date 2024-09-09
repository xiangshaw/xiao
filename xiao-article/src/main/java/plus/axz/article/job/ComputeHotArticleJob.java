package plus.axz.article.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import plus.axz.article.service.HotArticlesService;

/**
 * @author xiaoxiang
 * description 热点文章分值定时计算
 */
@RequiredArgsConstructor
@Component
@Log4j2
public class ComputeHotArticleJob {

    private final HotArticlesService hotArticlesService;

    // 和xxljob 的JobHandler 填的一定要保持一致
    @XxlJob("xcomputeHotArticleJob")
    public ReturnT<String> handle(String param) throws Exception {
        log.info("热文章分值计算调度任务开始执行....");
        hotArticlesService.computeHotArticle();
        log.info("热文章分值计算调度任务开始执行....");
        return ReturnT.SUCCESS;
    }
}
