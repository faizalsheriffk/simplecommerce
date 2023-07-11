package com.faizal.simplecommerce.sales.order.item;

import com.faizal.simplecommerce.common.primitives.Quantity;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Order Item entity.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class OrderItem {

    private final @NonNull ProductId productId;
    private final @NonNull Quantity quantity;

    public ProductId productId() {
        return productId;
    }

    public Quantity quantity() {
        return quantity;
    }
}
