package plus.axz.common.threadpool;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars 首先通过线程池中线程的重用，减少创建和销毁线程的性能开销。其次，能控制线程池中的并发数，否则会因为大量的线程争夺CPU资源造成阻塞。
 *
Reject策略预定义有四种：
  1  ThreadPoolExecutor.AbortPolicy策略，
    是默认的策略,处理程序遭到拒绝将抛出运行时 RejectedExecutionException。

  2  ThreadPoolExecutor.CallerRunsPolicy策略 ,
    调用者的线程会执行该任务,如果执行器已关闭,则丢弃.

  3  ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃.

  4  ThreadPoolExecutor.DiscardOldestPolicy策略，
    如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
 */
@Data
@Configuration
@EnableAsync //开启异步请求
public class ThreadPoolConfig {
    private static final int corePoolSize = 10;   // 核心线程数（默认线程数）
    private static final int maxPoolSize = 100;   // 最大线程数
    private static final int keepAliveTime = 10;  // 允许线程空闲时间（单位：默认为秒）
    private static final int queueCapacity = 500; // 缓冲队列数

    /**
     * 默认异步线程池
     * @return
     */
    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();/*新建线程池*/
        pool.setThreadNamePrefix("--------------全局线程池-----------------");
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        pool.setKeepAliveSeconds(keepAliveTime);
        pool.setQueueCapacity(queueCapacity);
        // 直接在execute方法的调用线程中运行
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());/*拒绝的策略*/
        // 初始化
        pool.initialize();
        return pool;
    }
}
