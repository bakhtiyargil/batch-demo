package az.baxtiyargil.batchdemo.batch;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class OrderItemWriter extends JpaItemWriter<OrderItem> {

    /**
     * Using plain JDBC to read entities causes the JPA write step to run two queries:
     * first to fetch the entity into the persistence context, then to perform the update.
     * Look: driving query pattern.
     */
    public OrderItemWriter(EntityManagerFactory managerFactory) {
        setEntityManagerFactory(managerFactory);
    }
}
