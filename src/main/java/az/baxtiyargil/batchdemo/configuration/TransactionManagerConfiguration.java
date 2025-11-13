package az.baxtiyargil.batchdemo.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TransactionManagerConfiguration {

    /**
     * Spring automatically detects this bean and uses it as the default transaction manager.
     * It coordinates transaction boundaries (commit/rollback) for JDBC operations performed
     * using the given {@code DataSource}.
     *
     * @param dataSource the DataSource to manage transactions for.
     * @return a new {@link org.springframework.jdbc.datasource.DataSourceTransactionManager}
     * used by the Batch Job Repository and all transaction-related operations in the project.
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * This is needed when the Spring Batch write step interacts with JPA.
     */
    @Bean
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
