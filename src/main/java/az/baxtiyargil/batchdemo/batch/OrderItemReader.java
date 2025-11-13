package az.baxtiyargil.batchdemo.batch;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import az.baxtiyargil.batchdemo.domain.OrderItemId;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
@StepScope
public class OrderItemReader extends JdbcCursorItemReader<OrderItem> {

    private static final String sql = """
                SELECT oi.order_id,
                       oi.line_item_id,
                       oi.product_id,
                       oi.unit_price,
                       oi.quantity,
                       oi.shipment_id
                  FROM ORDER_ITEMS oi WHERE oi.product_id = ?
            """;

    /**
     * Passing productId from JobParameters to execute a reader query with dynamic values.
     */
    public OrderItemReader(DataSource dataSource,
                           @Value("#{jobParameters['productId']}") Long productId) {
        setDataSource(dataSource);
        setSql(sql);
        setPreparedStatementSetter(ps -> ps.setLong(1, productId));
        setFetchSize(50);
        setRowMapper((rs, rowNum) -> {
            OrderItem item = new OrderItem();
            OrderItemId id = new OrderItemId();
            id.setOrderId(rs.getLong("order_id"));
            id.setLineItemId(rs.getLong("line_item_id"));
            item.setId(id);
            item.setProductId(rs.getLong("product_id"));
            item.setUnitPrice(rs.getBigDecimal("unit_price"));
            item.setQuantity(rs.getInt("quantity"));

            long shipmentId = rs.getLong("shipment_id");
            item.setShipmentId(shipmentId == 0 ? null : shipmentId);
            return item;
        });
    }
}
