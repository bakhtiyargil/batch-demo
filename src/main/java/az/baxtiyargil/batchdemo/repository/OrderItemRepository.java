package az.baxtiyargil.batchdemo.repository;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import az.baxtiyargil.batchdemo.domain.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

    List<OrderItem> findByIdOrderId(Long orderId);
}
