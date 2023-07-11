package com.faizal.simplecommerce.billing.payment;

import java.time.Instant;

import com.faizal.simplecommerce.common.events.DomainEvent;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Payment Collected domain event.
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = "referenceId")
@ToString
public final class PaymentCollected implements DomainEvent {

    public final @NonNull Instant when;
    public final @NonNull String referenceId;
}
