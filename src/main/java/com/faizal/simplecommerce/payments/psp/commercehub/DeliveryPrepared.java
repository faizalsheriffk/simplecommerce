package com.faizal.simplecommerce.payments.psp.commercehub;

import java.time.Instant;

import com.faizal.simplecommerce.common.events.DomainEvent;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Delivery Prepared domain event.
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = "orderId")
@ToString
public final class DeliveryPrepared implements DomainEvent {

    public final @NonNull Instant when;
    public final @NonNull String orderId;
}
