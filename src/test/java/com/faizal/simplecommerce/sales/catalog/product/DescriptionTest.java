package com.faizal.simplecommerce.sales.catalog.product;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.sales.catalog.product.Description;

import static org.assertj.core.api.Assertions.assertThat;

class DescriptionTest {

    @Test
    void description_value() {
        Description description = new Description("test");
        assertThat(description.value()).isEqualTo("test");
    }

    @Test
    void description_value_is_trimmed() {
        Description description = new Description("   test   ");
        assertThat(description.value()).isEqualTo("test");
    }
}
