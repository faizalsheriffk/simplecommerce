package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.psp.commercehub.Address;
import com.faizal.simplecommerce.payments.psp.commercehub.Delivery;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryPrepared;
import com.faizal.simplecommerce.payments.psp.commercehub.FindDeliveries;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;
import com.faizal.simplecommerce.payments.psp.commercehub.Person;
import com.faizal.simplecommerce.payments.psp.commercehub.Place;
import com.faizal.simplecommerce.payments.psp.commercehub.PrepareDelivery;
import com.faizal.simplecommerce.payments.psp.commercehub.jdbc.DeliveryJdbcConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = DeliveryJdbcConfig.class)
class PrepareDeliveryTest {

    @Autowired
    private FindDeliveries findDeliveries;
    @Autowired
    private PrepareDelivery prepareDelivery;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void delivery_for_order_is_prepared() {
        prepareDelivery.prepare(
                new OrderId("TEST123"),
                new Address(new Person("Test Person"), new Place("Test Address 123")));

        Delivery delivery = findDeliveries.byOrder(new OrderId("TEST123"));

        assertAll(
                () -> assertThat(delivery.orderId()).isEqualTo(new OrderId("TEST123")),
                () -> assertThat(delivery.address()).isEqualTo(new Address(new Person("Test Person"), new Place("Test Address 123")))
        );
    }

    @Test
    void prepared_delivery_raises_an_event() {
        prepareDelivery.prepare(
                new OrderId("TEST123"),
                new Address(new Person("Test Person"), new Place("Test Address 123")));

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(DeliveryPrepared.class);
                    DeliveryPrepared deliveryPrepared = (DeliveryPrepared) event;
                    assertAll(
                            () -> assertThat(deliveryPrepared.when).isNotNull(),
                            () -> assertThat(deliveryPrepared.orderId).isEqualTo("TEST123")
                    );
                    return true;
                }));
    }
}
