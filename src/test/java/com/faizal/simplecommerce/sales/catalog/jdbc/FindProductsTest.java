package com.faizal.simplecommerce.sales.catalog.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.catalog.FindProducts;
import com.faizal.simplecommerce.sales.catalog.jdbc.CatalogJdbcConfig;
import com.faizal.simplecommerce.sales.catalog.product.Description;
import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;
import com.faizal.simplecommerce.sales.catalog.product.Title;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = CatalogJdbcConfig.class)
@Sql("/test-data-sales-find-products.sql")
class FindProductsTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void all_products_are_found() {
        List<ProductId> productIds = findProducts.all().stream()
                .map(Product::id)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactlyInAnyOrder(
                new ProductId("p-1"), new ProductId("p-2"), new ProductId("p-3"));
    }

    @Test
    void product_by_id_is_found() {
        Product product = findProducts.byId(new ProductId("p-1"));
        assertAll(
                () -> assertThat(product.id()).isEqualTo(new ProductId("p-1")),
                () -> assertThat(product.title()).isEqualTo(new Title("Prod 1")),
                () -> assertThat(product.description()).isEqualTo(new Description("Prod 1 Desc")),
                () -> assertThat(product.price()).isEqualTo(new Money(1.f))
        );
    }

    @Test
    void unknown_product_found_for_an_unknown_id() {
        Product product = findProducts.byId(new ProductId("there's no such an ID"));

        assertThat(product.id()).isEqualTo(new ProductId(0));
    }
}
