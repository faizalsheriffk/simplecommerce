package com.faizal.simplecommerce.payments.psp.commercehub;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderIdTest {

    @Test
    void string_id_value() {
        OrderId orderId = new OrderId(123L);
        assertThat(orderId.value()).isEqualTo("123");
    }

    @Test
    void fails_for_null() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId(null));
    }
}
