package com.faizal.simplecommerce.payments.orchestrator;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.payments.orchestrator.Amount;
import com.faizal.simplecommerce.payments.orchestrator.ProductId;
import com.faizal.simplecommerce.payments.orchestrator.ToFetch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ToFetchTest {

    @Test
    void to_fetch_values() {
        ToFetch toFetch = new ToFetch(new ProductId("test"), new Amount(123));
        assertAll(
                () -> assertThat(toFetch.productId()).isEqualTo(new ProductId("test")),
                () -> assertThat(toFetch.amount()).isEqualTo(new Amount(123))
        );
    }
}
