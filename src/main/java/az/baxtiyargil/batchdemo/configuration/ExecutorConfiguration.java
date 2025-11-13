package az.baxtiyargil.batchdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import java.io.Serial;

@Configuration
@EnableScheduling
public class ExecutorConfiguration {

    @Bean("JobStepTaskExecutor")
    public TaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Task-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

    @Bean
    @Primary
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler() {
            @Serial
            private static final long serialVersionUID = -1L;

            @Override
            public void destroy() {
                this.getScheduledThreadPoolExecutor()
                        .setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
                super.destroy();
            }
        };
        scheduler.setPoolSize(9);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(60);

        return scheduler;
    }
}
