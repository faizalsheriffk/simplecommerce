package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.faizal.simplecommerce.payments.psp.commercehub.Delivery;
import com.faizal.simplecommerce.payments.psp.commercehub.DispatchDelivery;
import com.faizal.simplecommerce.payments.psp.commercehub.FindDeliveries;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Dispatch Delivery use-case.
 */
@RequiredArgsConstructor
public class DispatchDeliveryJdbc implements DispatchDelivery {

    private final @NonNull FindDeliveries findDeliveries;

    /**
     * Dispatches a delivery by the order ID.
     *
     * @param orderId the order ID
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void byOrder(OrderId orderId) {
        Delivery delivery = findDeliveries.byOrder(orderId);
        delivery.dispatch();
    }
}
