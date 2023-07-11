package com.faizal.simplecommerce.payments.orchestrator.listeners;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.faizal.simplecommerce.payments.mastertransactions.DeliveryDispatched;
import com.faizal.simplecommerce.payments.orchestrator.OrderId;
import com.faizal.simplecommerce.payments.orchestrator.RemoveFetchedGoods;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Warehouse listener for DeliveryDispatched event.
 */
@Component("warehouse-deliveryDispatchedListener") // a custom name to avoid collision
@RequiredArgsConstructor
class DeliveryDispatchedListener {

    private final @NonNull RemoveFetchedGoods removeFetchedGoods;

    @TransactionalEventListener
    @Async
    public void on(DeliveryDispatched event) {
        removeFetchedGoods.removeForOrder(new OrderId(event.orderId));
    }
}
