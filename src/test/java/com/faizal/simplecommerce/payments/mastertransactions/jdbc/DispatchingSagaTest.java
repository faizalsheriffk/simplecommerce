package com.faizal.simplecommerce.payments.mastertransactions.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.mastertransactions.Dispatching;
import com.faizal.simplecommerce.payments.mastertransactions.DispatchingSaga;
import com.faizal.simplecommerce.payments.mastertransactions.OrderId;
import com.faizal.simplecommerce.payments.mastertransactions.jdbc.DispatchingJdbcConfig;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@JdbcTest
@ContextConfiguration(classes = DispatchingJdbcConfig.class)
class DispatchingSagaTest {

    @Autowired
    private DispatchingSaga saga;

    @MockBean
    private Dispatching dispatching;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void delivery_is_dispatched() {
        saga.prepared(new OrderId("TEST"));
        saga.accepted(new OrderId("TEST"));
        saga.fetched(new OrderId("TEST"));
        saga.paid(new OrderId("TEST"));

        verify(dispatching).dispatch(new OrderId("TEST"));
    }

    @Test
    void not_paid_delivery_is_not_dispatched() {
        saga.prepared(new OrderId("TEST"));
        saga.accepted(new OrderId("TEST"));
        saga.fetched(new OrderId("TEST"));
        //saga.paid(new SagaId("TEST"));

        verifyNoInteractions(dispatching);
    }

    @Test
    void not_fetched_delivery_is_not_dispatched() {
        saga.prepared(new OrderId("TEST"));
        saga.accepted(new OrderId("TEST"));
        //saga.fetched(new SagaId("TEST"));
        saga.paid(new OrderId("TEST"));

        verifyNoInteractions(dispatching);
    }

    @Test
    void not_accepted_delivery_is_not_dispatched() {
        saga.prepared(new OrderId("TEST"));
        //saga.accepted(new SagaId("TEST"));
        saga.fetched(new OrderId("TEST"));
        saga.paid(new OrderId("TEST"));

        verifyNoInteractions(dispatching);
    }

    @Test
    void not_prepared_delivery_is_not_dispatched() {
        //saga.prepared(new SagaId("TEST"));
        saga.accepted(new OrderId("TEST"));
        saga.fetched(new OrderId("TEST"));
        saga.paid(new OrderId("TEST"));

        verifyNoInteractions(dispatching);
    }
}
