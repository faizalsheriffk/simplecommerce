package com.faizal.simplecommerce.sales.order;

import java.time.Instant;
import java.util.Map;

import com.faizal.simplecommerce.common.events.DomainEvent;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Order Placed domain event.
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = "orderId")
@ToString
public final class OrderPlaced implements DomainEvent {

    public final @NonNull Instant when;
    public final @NonNull String orderId;
    public final @NonNull Map<String, Integer> items;
    public final @NonNull Float total;
}
