package com.faizal.simplecommerce.payments.psp.commercehub.listeners;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.faizal.simplecommerce.payments.mastertransactions.DeliveryDispatched;
import com.faizal.simplecommerce.payments.psp.commercehub.DispatchDelivery;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class DeliveryDispatchedListener {

    private final @NonNull DispatchDelivery dispatchDelivery;

    @TransactionalEventListener
    @Async
    public void on(DeliveryDispatched event) {
        dispatchDelivery.byOrder(new OrderId(event.orderId));
    }
}
