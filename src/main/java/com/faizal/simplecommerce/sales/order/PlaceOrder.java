package com.faizal.simplecommerce.sales.order;

import java.util.Collection;

import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.order.item.OrderItem;

/**
 * Place Order use-case.
 */
public interface PlaceOrder {

    /**
     * Places a new order.
     *
     * @param orderId the order ID
     * @param items   the order items
     */
    void place(OrderId orderId, Collection<OrderItem> items, Money total);
}
