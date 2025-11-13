package az.baxtiyargil.batchdemo.batch;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class OrderItemProcessor implements ItemProcessor<OrderItem, OrderItem> {

    @Override
    public OrderItem process(OrderItem item) {
        item.setQuantity(item.getQuantity() + 1);
        return item;
    }
}
