package com.faizal.simplecommerce.payments.mastertransactions.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.mastertransactions.DeliveryDispatched;
import com.faizal.simplecommerce.payments.mastertransactions.Dispatching;
import com.faizal.simplecommerce.payments.mastertransactions.OrderId;
import com.faizal.simplecommerce.payments.mastertransactions.jdbc.DispatchingJdbcConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = DispatchingJdbcConfig.class)
class DispatchingTest {

    @Autowired
    private Dispatching dispatching;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void dispatching_a_delivery_raises_an_event() {
        dispatching.dispatch(new OrderId("DispatchingTest"));

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(DeliveryDispatched.class);
                    DeliveryDispatched deliveryDispatched = (DeliveryDispatched) event;
                    assertAll(
                            () -> assertThat(deliveryDispatched.when).isNotNull(),
                            () -> assertThat(deliveryDispatched.orderId).isEqualTo("DispatchingTest")
                    );
                    return true;
                }));
    }
}
