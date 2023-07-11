package com.faizal.simplecommerce.sales.order.jdbc;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.order.OrderId;
import com.faizal.simplecommerce.sales.order.PlaceOrder;
import com.faizal.simplecommerce.sales.order.item.OrderItem;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC implementation for Place Order use-cases.
 */
@RequiredArgsConstructor
@Slf4j
class PlaceOrderJdbc implements PlaceOrder {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void place(@NonNull OrderId orderId, @NonNull Collection<OrderItem> items, @NonNull Money total) {
        new OrderJdbc(orderId, total, items, jdbcTemplate, eventPublisher)
                .place();
    }
}
