package az.baxtiyargil.batchdemo.batch;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class OrderItemWriter extends JpaItemWriter<OrderItem> {

    public OrderItemWriter(EntityManagerFactory managerFactory) {
        setEntityManagerFactory(managerFactory);
    }
}
