package com.faizal.simplecommerce.sales.cart.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import com.faizal.simplecommerce.sales.cart.Cart;
import com.faizal.simplecommerce.sales.cart.CartId;
import com.faizal.simplecommerce.sales.cart.RetrieveCart;
import com.faizal.simplecommerce.sales.cart.jdbc.CartJdbcConfig;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = CartJdbcConfig.class)
class RetrieveCartTest {

    @Autowired
    private RetrieveCart retrieveCart;

    @Test
    void cart_is_retrieved() {
        Cart cart = retrieveCart.byId(new CartId("TEST"));
        assertThat(cart.id()).isEqualTo(new CartId("TEST"));
    }
}
