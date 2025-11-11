package az.baxtiyargil.batchdemo.service;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import az.baxtiyargil.batchdemo.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByIdOrderId(orderId);
    }
}
