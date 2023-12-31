package com.faizal.simplecommerce.payments.mastertransactions;

/**
 * Dispatching use-case.
 */
public interface Dispatching {

    /**
     * Dispatch an order by the order ID.
     *
     * @param orderId the order ID.
     */
    void dispatch(OrderId orderId);
}
