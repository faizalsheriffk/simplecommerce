package com.faizal.simplecommerce.sales.catalog.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.sales.catalog.FindProducts;
import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;
import com.faizal.simplecommerce.sales.catalog.product.Products;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC implementation for Find Products use-cases.
 */
@RequiredArgsConstructor
@Slf4j
final class FindProductsJdbc implements FindProducts {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Products all() {
        return new ProductsJdbc(
                "SELECT id, title, description, price FROM products",
                jdbcTemplate);
    }

    @Override
    public Product byId(ProductId id) {
        return new ProductsJdbc(
                "SELECT id, title, description, price FROM products WHERE id = ?",
                id.value(), jdbcTemplate).stream()
                .findFirst()
                .orElseGet(UnknownProduct::new);
    }
}
