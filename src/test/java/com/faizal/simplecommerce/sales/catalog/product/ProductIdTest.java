package com.faizal.simplecommerce.sales.catalog.product;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.sales.catalog.product.ProductId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductIdTest {

    @Test
    void string_id_value() {
        ProductId productId = new ProductId(123);
        assertThat(productId.value()).isEqualTo("123");
    }

    @Test
    void fails_for_null() {
        assertThrows(IllegalArgumentException.class, () -> new ProductId(null));
    }
}
