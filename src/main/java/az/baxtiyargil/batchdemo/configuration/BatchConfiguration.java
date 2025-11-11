package az.baxtiyargil.batchdemo.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    /**
     * Creates and configures a {@link JobRepository} for Spring Batch.
     * <p><strong>Note:</strong> Actually, manually defining this bean is not required
     * when using Spring Boot, as it autoconfigures a JobRepository if @EnableBatchProcessing.
     * It is added here for clarification and explicit control.</p>
     */
    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
            throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    /**
     * Creates and configures a {@link JobLauncher} for Spring Batch.
     * <p><strong>Note:</strong> Actually, manually defining this bean is not required
     * when using Spring Boot, as it autoconfigures a JobLauncher if @EnableBatchProcessing.
     * It is added here for clarification and explicit control.</p>
     * You can set {@link org.springframework.core.task.TaskExecutor} for async execution.
     */
    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher launcher = new TaskExecutorJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.afterPropertiesSet();
        return launcher;
    }
}
