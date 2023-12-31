package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.payments.psp.commercehub.Delivery;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryId;
import com.faizal.simplecommerce.payments.psp.commercehub.jdbc.UnknownDelivery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UnknownDeliveryTest {

    @Test
    void unknown_delivery_has_values() {
        Delivery unknownDelivery = new UnknownDelivery();
        assertAll(
                () -> assertThat(unknownDelivery.id()).isEqualTo(new DeliveryId(0)),
                () -> assertThat(unknownDelivery.orderId()).isNotNull(),
                () -> assertThat(unknownDelivery.address()).isNotNull()
        );
    }

    @Test
    void prepare_noop() {
        Delivery unknownDelivery = new UnknownDelivery();
        unknownDelivery.prepare();

        assertThat(new UnknownDelivery().isDispatched()).isFalse();
    }

    @Test
    void dispatch_noop() {
        Delivery unknownDelivery = new UnknownDelivery();
        unknownDelivery.dispatch();

        assertThat(new UnknownDelivery().isDispatched()).isFalse();
    }

    @Test
    void unknown_delivery_is_not_dispatched() {
        assertThat(new UnknownDelivery().isDispatched()).isFalse();
    }
}
