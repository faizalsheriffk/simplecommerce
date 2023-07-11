package com.faizal.simplecommerce.billing.payment.listeners;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.faizal.simplecommerce.billing.payment.CollectPayment;
import com.faizal.simplecommerce.billing.payment.ReferenceId;
import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.order.OrderPlaced;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Payment listener for OrderPlaced event.
 */
@Component("payment-orderPlacedListener") // a custom name to avoid collision
@RequiredArgsConstructor
class OrderPlacedListener {

    private final @NonNull CollectPayment collectPayment;

    @TransactionalEventListener
    @Async
    public void on(OrderPlaced event) {
        collectPayment.collect(
                new ReferenceId(event.orderId),
                new Money(event.total));
    }
}
