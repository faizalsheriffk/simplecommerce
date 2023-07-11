package com.faizal.simplecommerce.sales.cart.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.sales.cart.Cart;
import com.faizal.simplecommerce.sales.cart.CartId;
import com.faizal.simplecommerce.sales.cart.RetrieveCart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC implementation for Retrieve Cart use-cases.
 */
@RequiredArgsConstructor
class RetrieveCartJdbc implements RetrieveCart {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Cart byId(CartId cartId) {
        return new CartJdbc(cartId, jdbcTemplate);
    }
}
