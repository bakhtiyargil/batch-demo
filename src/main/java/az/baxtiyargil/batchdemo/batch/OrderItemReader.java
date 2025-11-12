package az.baxtiyargil.batchdemo.batch;

import az.baxtiyargil.batchdemo.domain.OrderItem;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
@StepScope
public class OrderItemReader extends JdbcCursorItemReader<OrderItem> {

    private static final String sql = "SELECT id, name, price FROM product WHERE product_id = ?";

    /**
     * Passing productId from JobParameters to execute a reader query with dynamic values.
     */
    public OrderItemReader(DataSource dataSource,
                           @Value("#{jobParameters['productId']}") Long productId) {
        setDataSource(dataSource);
        setSql(sql);
        setPreparedStatementSetter(ps -> ps.setLong(1, productId));
        setFetchSize(50);
        setRowMapper(new BeanPropertyRowMapper<>(OrderItem.class));
    }

}
