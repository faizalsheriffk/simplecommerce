package com.faizal.simplecommerce.payments.mastertransactions.listeners;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.faizal.simplecommerce.billing.payment.PaymentCollected;
import com.faizal.simplecommerce.payments.mastertransactions.DispatchingSaga;
import com.faizal.simplecommerce.payments.mastertransactions.OrderId;
import com.faizal.simplecommerce.payments.orchestrator.GoodsFetched;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryPrepared;
import com.faizal.simplecommerce.sales.order.OrderPlaced;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class DispatchingListeners {

    private final @NonNull DispatchingSaga saga;

    @TransactionalEventListener
    @Async
    public void on(DeliveryPrepared event) {
        saga.prepared(new OrderId(event.orderId));
    }

    @TransactionalEventListener
    @Async
    public void on(OrderPlaced event) {
        saga.accepted(new OrderId(event.orderId));
    }

    @TransactionalEventListener
    @Async
    public void on(GoodsFetched event) {
        saga.fetched(new OrderId(event.orderId));
    }

    @TransactionalEventListener
    @Async
    public void on(PaymentCollected event) {
        saga.paid(new OrderId(event.referenceId));
    }
}
