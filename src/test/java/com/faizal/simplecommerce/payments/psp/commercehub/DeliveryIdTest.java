package com.faizal.simplecommerce.payments.psp.commercehub;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryIdTest {

    @Test
    void string_id_value() {
        DeliveryId deliveryId = new DeliveryId(123L);
        assertThat(deliveryId.value()).isEqualTo("123");
    }

    @Test
    void fails_for_null() {
        assertThrows(IllegalArgumentException.class, () -> new DeliveryId(null));
    }
}
