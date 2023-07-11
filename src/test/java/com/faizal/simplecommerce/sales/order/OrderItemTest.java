package com.faizal.simplecommerce.sales.order;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.common.primitives.Quantity;
import com.faizal.simplecommerce.sales.order.item.OrderItem;
import com.faizal.simplecommerce.sales.order.item.ProductId;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    void order_item_is_created() {
        OrderItem orderItem = new OrderItem(new ProductId("test-1"), new Quantity(123));
        assertThat(orderItem.quantity()).isEqualTo(new Quantity(123));
    }
}
