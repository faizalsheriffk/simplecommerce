package com.faizal.simplecommerce.payments.psp.commercehub;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Delivery Info entity.
 * <p>
 * Basic information about Delivery.
 */
@EqualsAndHashCode(of = "deliveryId")
@RequiredArgsConstructor
@ToString
public final class DeliveryInfo {

    private final @NonNull DeliveryId deliveryId;
    private final @NonNull OrderId orderId;

    public DeliveryId id() {
        return deliveryId;
    }

    public OrderId orderId() {
        return orderId;
    }
}
