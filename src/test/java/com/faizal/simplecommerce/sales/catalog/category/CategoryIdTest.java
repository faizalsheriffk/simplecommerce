package com.faizal.simplecommerce.sales.catalog.category;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.sales.catalog.category.CategoryId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryIdTest {

    @Test
    void string_id_value() {
        CategoryId categoryId = new CategoryId(123L);
        assertThat(categoryId.value()).isEqualTo("123");
    }

    @Test
    void fails_for_null() {
        assertThrows(IllegalArgumentException.class, () -> new CategoryId(null));
    }
}
