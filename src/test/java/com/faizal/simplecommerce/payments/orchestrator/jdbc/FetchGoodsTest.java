package com.faizal.simplecommerce.payments.orchestrator.jdbc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.orchestrator.Amount;
import com.faizal.simplecommerce.payments.orchestrator.FetchGoods;
import com.faizal.simplecommerce.payments.orchestrator.GoodsFetched;
import com.faizal.simplecommerce.payments.orchestrator.GoodsMissed;
import com.faizal.simplecommerce.payments.orchestrator.InStock;
import com.faizal.simplecommerce.payments.orchestrator.OrderId;
import com.faizal.simplecommerce.payments.orchestrator.ProductId;
import com.faizal.simplecommerce.payments.orchestrator.ToFetch;
import com.faizal.simplecommerce.payments.orchestrator.Warehouse;
import com.faizal.simplecommerce.payments.orchestrator.jdbc.WarehouseJdbcConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = WarehouseJdbcConfig.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class FetchGoodsTest {

    @Autowired
    private FetchGoods fetchGoods;
    @Autowired
    private Warehouse warehouse;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void fetched_goods_raises_an_event() {
        String productCode = productCodeInStock(1);
        fetchGoods.fetchFromOrder(new OrderId("TEST123"), List.of(
                new ToFetch(new ProductId(productCode), new Amount(1))));

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(GoodsFetched.class);
                    GoodsFetched goodsFetched = (GoodsFetched) event;
                    assertAll(
                            () -> assertThat(goodsFetched.when).isNotNull(),
                            () -> assertThat(goodsFetched.orderId).isEqualTo("TEST123")
                    );
                    return true;
                }));
    }

    @Test
    void fetching_decrease_amount_in_the_stock() {
        String productCode = productCodeInStock(2);
        fetchGoods.fetchFromOrder(new OrderId(123), List.of(
                new ToFetch(new ProductId(productCode), new Amount(1))));

        assertThat(warehouse.leftInStock(new ProductId(productCode))).isEqualTo(new InStock(new Amount(1)));
    }

    @Test
    void cannot_decrease_amount_under_zero() {
        String productCode = productCodeInStock(1);
        fetchGoods.fetchFromOrder(new OrderId(123), List.of(
                new ToFetch(new ProductId(productCode), new Amount(2))));

        assertThat(warehouse.leftInStock(new ProductId(productCode))).isEqualTo(new InStock(Amount.ZERO));
    }

    @Test
    void missed_goods_raises_an_event() {
        String productCode = productCodeInStock(1);
        fetchGoods.fetchFromOrder(new OrderId(123), List.of(
                new ToFetch(new ProductId(productCode), new Amount(99))));

        verify(eventPublisher, atLeastOnce()).raise(eq(
                new GoodsMissed(Instant.now(), productCode, 98)));
    }

    String productCodeInStock(int amount) {
        String productCode = UUID.randomUUID().toString();
        warehouse.putIntoStock(new ProductId(productCode), new Amount(amount));
        return productCode;
    }
}
