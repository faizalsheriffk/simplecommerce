package com.faizal.simplecommerce.payments.orchestrator;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Warehouse To Fetch Order ID domain primitive.
 */
@EqualsAndHashCode
@ToString
public final class OrderId {

    private final @NonNull String id;

    public OrderId(@NonNull Object id) {
        var idVal = id.toString().strip();
        if (idVal.isBlank()) {
            throw new IllegalArgumentException("ID cannot be empty!");
        }
        this.id = idVal;
    }

    public String value() {
        return id;
    }
}