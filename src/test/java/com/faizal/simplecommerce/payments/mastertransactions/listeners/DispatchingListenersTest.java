package com.faizal.simplecommerce.payments.mastertransactions.listeners;

import java.time.Instant;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.faizal.simplecommerce.billing.payment.PaymentCollected;
import com.faizal.simplecommerce.payments.mastertransactions.DispatchingSaga;
import com.faizal.simplecommerce.payments.mastertransactions.OrderId;
import com.faizal.simplecommerce.payments.mastertransactions.listeners.DispatchingListeners;
import com.faizal.simplecommerce.payments.orchestrator.GoodsFetched;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryPrepared;
import com.faizal.simplecommerce.sales.order.OrderPlaced;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DispatchingListenersTest {

    @Test
    void prepared_called() {
        DispatchingSaga saga = mock(DispatchingSaga.class);
        DispatchingListeners listeners = new DispatchingListeners(saga);

        listeners.on(new DeliveryPrepared(Instant.now(), "TEST123"));

        verify(saga).prepared(new OrderId("TEST123"));
    }

    @Test
    void accepted_called() {
        DispatchingSaga saga = mock(DispatchingSaga.class);
        DispatchingListeners listeners = new DispatchingListeners(saga);

        listeners.on(new OrderPlaced(Instant.now(), "TEST123", Collections.emptyMap(), 1.f));

        verify(saga).accepted(new OrderId("TEST123"));
    }

    @Test
    void fetched_called() {
        DispatchingSaga saga = mock(DispatchingSaga.class);
        DispatchingListeners listeners = new DispatchingListeners(saga);

        listeners.on(new GoodsFetched(Instant.now(), "TEST123"));

        verify(saga).fetched(new OrderId("TEST123"));
    }

    @Test
    void paid_called() {
        DispatchingSaga saga = mock(DispatchingSaga.class);
        DispatchingListeners listeners = new DispatchingListeners(saga);

        listeners.on(new PaymentCollected(Instant.now(), "TEST123"));

        verify(saga).paid(new OrderId("TEST123"));
    }
}
