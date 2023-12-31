package com.faizal.simplecommerce.sales.order.jdbc;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.common.primitives.Quantity;
import com.faizal.simplecommerce.sales.order.Order;
import com.faizal.simplecommerce.sales.order.OrderId;
import com.faizal.simplecommerce.sales.order.OrderPlaced;
import com.faizal.simplecommerce.sales.order.PlaceableOrder;
import com.faizal.simplecommerce.sales.order.item.OrderItem;
import com.faizal.simplecommerce.sales.order.item.ProductId;
import com.faizal.simplecommerce.sales.order.jdbc.OrderJdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@JdbcTest
class OrderTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void items_are_returned() {
        Order order = new OrderJdbc(new OrderId("TEST123"), new Money(3.f), List.of(
                new OrderItem(new ProductId("test-1"), new Quantity(1)),
                new OrderItem(new ProductId("test-2"), new Quantity(2))),
                jdbcTemplate, eventPublisher);
        assertAll(
                () -> assertThat(order.items()).hasSize(2),
                () -> assertThat(order.items().get(0).quantity()).isEqualTo(new Quantity(1)),
                () -> assertThat(order.items().get(1).quantity()).isEqualTo(new Quantity(2))
        );
    }

    @Test
    void order_contains_at_least_one_item() {
        assertThrows(Order.OrderHasNoItemsException.class,
                     () -> new OrderJdbc(new OrderId("TEST123"), Money.ZERO, Collections.emptyList(),
                                         jdbcTemplate, eventPublisher));
    }

    @Test
    void placed_order_raises_an_event() {
        PlaceableOrder order = new OrderJdbc(new OrderId("TEST123"), new Money(12.34f * 123), List.of(
                new OrderItem(new ProductId("test-1"), new Quantity(123))),
                jdbcTemplate, eventPublisher);
        order.place();

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(OrderPlaced.class);
                    OrderPlaced orderPlaced = (OrderPlaced) event;
                    assertAll(
                            () -> assertThat(orderPlaced.when).isNotNull(),
                            () -> assertThat(orderPlaced.orderId).isEqualTo("TEST123"),
                            () -> assertThat(orderPlaced.items).hasSize(1),
                            () -> assertThat(orderPlaced.items.get("test-1")).isEqualTo(123),
                            () -> assertThat(orderPlaced.total).isCloseTo(12.34f * 123, offset(0.01f))
                    );
                    return true;
                }));
    }

    @Test
    void order_can_be_placed_only_once() {
        PlaceableOrder order = new OrderJdbc(
                new OrderId("TEST123"), new Money(12.34f),
                List.of(new OrderItem(new ProductId("test-1"), new Quantity(123))),
                jdbcTemplate, eventPublisher);
        order.place();

        assertAll(
                () -> assertThrows(PlaceableOrder.OrderAlreadyPlacedException.class, () -> order.place()),
                () -> verify(eventPublisher, only()).raise(any(OrderPlaced.class))    // not two events
        );
    }
}
