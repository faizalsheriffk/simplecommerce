package com.faizal.simplecommerce.payments.psp.commercehub;

/**
 * Delivery entity.
 */
public interface Delivery {

    DeliveryId id();

    OrderId orderId();

    Address address();

    /**
     * @throws {@link DeliveryAlreadyPreparedException} when already prepared.
     */
    void prepare();

    /**
     * @throws {@link DeliveryAlreadyDispatchedException} when already dispatched.
     */
    void dispatch();

    boolean isDispatched();

    final class DeliveryAlreadyPreparedException extends IllegalStateException {
    }

    final class DeliveryAlreadyDispatchedException extends IllegalStateException {
    }
}
