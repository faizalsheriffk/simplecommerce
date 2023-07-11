package com.faizal.simplecommerce.sales.order.jdbc;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.sales.order.Order;
import com.faizal.simplecommerce.sales.order.OrderId;
import com.faizal.simplecommerce.sales.order.jdbc.UnknownOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UnknownOrderTest {

    @Test
    void unknown_product_has_values() {
        Order unknownOrder = new UnknownOrder();
        assertAll(
                () -> assertThat(unknownOrder.id()).isEqualTo(new OrderId(0)),
                () -> assertThat(unknownOrder.items()).hasSize(0)
        );
    }
}
