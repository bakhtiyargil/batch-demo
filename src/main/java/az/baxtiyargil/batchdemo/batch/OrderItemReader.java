package az.baxtiyargil.batchdemo.batch;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import az.baxtiyargil.batchdemo.domain.OrderItemId;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@StepScope
public class OrderItemReader extends JdbcPagingItemReader<OrderItem> {

    /**
     * Passing productId from JobParameters to execute a reader query with dynamic values.
     */
    public OrderItemReader(DataSource dataSource,
                           @Value("#{jobParameters['productId']}") Long productId) throws Exception {
        setDataSource(dataSource);
        setFetchSize(50);
        setPageSize(50);
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

        PagingQueryProvider queryProvider = getQueryProvider(dataSource);
        setQueryProvider(queryProvider);
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("productId", productId);
        setParameterValues(parameterValues);
    }

    private static PagingQueryProvider getQueryProvider(DataSource dataSource) throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("SELECT oi.order_id, oi.line_item_id, oi.product_id, oi.unit_price, oi.quantity, oi.shipment_id");
        queryProvider.setFromClause("FROM ORDER_ITEMS oi");
        queryProvider.setWhereClause("WHERE oi.product_id = :productId");
        queryProvider.setSortKeys(Map.of(
                "order_id", Order.ASCENDING,
                "line_item_id", Order.ASCENDING
        ));
        return queryProvider.getObject();
    }
}
