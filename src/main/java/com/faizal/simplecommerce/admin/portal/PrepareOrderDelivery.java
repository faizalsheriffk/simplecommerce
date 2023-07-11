package com.faizal.simplecommerce.admin.portal;

import java.util.UUID;

import com.faizal.simplecommerce.payments.psp.commercehub.Address;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;
import com.faizal.simplecommerce.payments.psp.commercehub.PrepareDelivery;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Prepare Delivery for Order use-case.
 */
@RequiredArgsConstructor
public class PrepareOrderDelivery {

    private final @NonNull PrepareDelivery prepareDelivery;

    /**
     * Prepare a new delivery for the order.
     *
     * @param orderId the order ID
     * @param address the delivery address
     */
    public void prepareDelivery(@NonNull UUID orderId, @NonNull Address address) {
        // here a command message PrepareDelivery could be sent for lower coupling
        prepareDelivery.prepare(new OrderId(orderId), address);
    }
}
