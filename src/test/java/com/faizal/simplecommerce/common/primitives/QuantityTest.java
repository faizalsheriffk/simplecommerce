package com.faizal.simplecommerce.common.primitives;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.common.primitives.Quantity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuantityTest {

    @Test
    void quantity_value() {
        Quantity quantity = new Quantity(123);
        assertThat(quantity.value()).isEqualTo(123);
    }

    @Test
    void zero_quantity_has_a_zero_value() {
        assertThat(Quantity.ZERO.value()).isEqualTo(0);
    }

    @Test
    void one_quantity_has_a_value_equals_one() {
        assertThat(Quantity.ONE.value()).isEqualTo(1);
    }

    @Test
    void quantity_fails_for_a_value_less_than_zero() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(-1));
    }

    @Test
    void quantity_is_added() {
        Quantity sum = new Quantity(1).add(new Quantity(2));
        assertThat(sum).isEqualTo(new Quantity(3));
    }
}
