package com.faizal.simplecommerce.payments.psp.commercehub;

/**
 * Dispatch Delivery use-case.
 */
public interface DispatchDelivery {

    /**
     * Dispatches a delivery by the order ID.
     *
     * @param orderId the order ID
     */
    void byOrder(OrderId orderId);
}
