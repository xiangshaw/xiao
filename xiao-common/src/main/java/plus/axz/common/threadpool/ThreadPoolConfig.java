package plus.axz.common.threadpool;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xiaoxiang
 * description 首先通过线程池中线程的重用，减少创建和销毁线程的性能开销。其次，能控制线程池中的并发数，否则会因为大量的线程争夺CPU资源造成阻塞。
 * Reject策略预定义有四种：
 * 1  ThreadPoolExecutor.AbortPolicy策略，
 * 是默认的策略,处理程序遭到拒绝将抛出运行时 RejectedExecutionException。
 * 2  ThreadPoolExecutor.CallerRunsPolicy策略 ,
 * 调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
 * 3  ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃.
 * 4  ThreadPoolExecutor.DiscardOldestPolicy策略，
 * 如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
 */
@Data
@Configuration
//开启异步请求
@EnableAsync
public class ThreadPoolConfig {
    // 核心线程数（默认线程数）
    private static final int CORE_POOL_SIZE = 10;
    // 最大线程数
    private static final int MAX_POOL_SIZE = 100;
    // 允许线程空闲时间（单位：默认为秒）
    private static final int KEEP_ALIVE_TIME = 10;
    // 缓冲队列数
    private static final int QUEUE_CAPACITY = 500;

    /**
     * 默认异步线程池
     */
    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        // 新建线程池
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadNamePrefix("--------------全局线程池-----------------");
        pool.setCorePoolSize(CORE_POOL_SIZE);
        pool.setMaxPoolSize(MAX_POOL_SIZE);
        pool.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        pool.setQueueCapacity(QUEUE_CAPACITY);
        // 直接在execute方法的调用线程中运行
        // 拒绝的策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        pool.initialize();
        return pool;
    }
}
