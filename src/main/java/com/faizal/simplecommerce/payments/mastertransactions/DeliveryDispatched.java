package com.faizal.simplecommerce.payments.mastertransactions;

import java.time.Instant;

import com.faizal.simplecommerce.common.events.DomainEvent;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Delivery Dispatched domain event.
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = "orderId")
@ToString
public final class DeliveryDispatched implements DomainEvent {

    public final @NonNull Instant when;
    public final @NonNull String orderId;
}
