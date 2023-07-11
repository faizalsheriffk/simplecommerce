package com.faizal.simplecommerce.sales.order.jdbc;

import java.util.Collections;
import java.util.List;

import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.order.Order;
import com.faizal.simplecommerce.sales.order.OrderId;
import com.faizal.simplecommerce.sales.order.item.OrderItem;

import lombok.ToString;

/**
 * Null object implementation for Order entity.
 */
@ToString
final class UnknownOrder implements Order {

    @Override
    public OrderId id() {
        return new OrderId(0);
    }

    @Override
    public List<OrderItem> items() {
        return Collections.emptyList();
    }

    @Override
    public Money total() {
        return Money.ZERO;
    }
}
