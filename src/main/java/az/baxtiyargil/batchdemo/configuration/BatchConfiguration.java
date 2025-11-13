package az.baxtiyargil.batchdemo.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfiguration {

    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    public BatchConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
    }

    /**
     * Needed when Spring Batch tables are created.
     */
    @Bean
    public BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(BatchProperties batchProperties) {
        return new BatchDataSourceScriptDatabaseInitializer(dataSource, batchProperties.getJdbc());
    }

    /**
     * Creates and configures a {@link JobRepository} for Spring Batch.
     * <p><strong>Note:</strong> Actually, manually defining this bean is not required
     * when using Spring Boot, as it autoconfigures a JobRepository if @EnableBatchProcessing.
     * It is added here for clarification and explicit control.</p>
     */
    @Bean
    @Override
    public @NonNull JobRepository jobRepository() {
        return super.jobRepository();
    }

    /**
     * Creates and configures a {@link JobLauncher} for Spring Batch.
     * <p><strong>Note:</strong> Actually, manually defining this bean is not required
     * when using Spring Boot, as it autoconfigures a JobLauncher if @EnableBatchProcessing.
     * It is added here for clarification and explicit control.</p>
     * You can set {@link org.springframework.core.task.TaskExecutor} for async execution.
     */
    @Bean
    @Override
    public @NonNull JobLauncher jobLauncher(@NonNull JobRepository jobRepository) {
        return super.jobLauncher(jobRepository);
    }

    @Override
    public @NonNull DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public @NonNull PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }
}
