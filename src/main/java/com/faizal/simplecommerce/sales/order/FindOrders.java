package com.faizal.simplecommerce.sales.order;

/**
 * Find Orders use-case.
 */
public interface FindOrders {

    /**
     * Finds an order by the order ID.
     *
     * @param id the order ID
     * @return the order
     */
    Order byId(OrderId id);
}
