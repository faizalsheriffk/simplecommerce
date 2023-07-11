package com.faizal.simplecommerce.sales.catalog.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.catalog.FindProducts;
import com.faizal.simplecommerce.sales.catalog.jdbc.CatalogJdbcConfig;
import com.faizal.simplecommerce.sales.catalog.jdbc.ProductJdbc;
import com.faizal.simplecommerce.sales.catalog.product.Description;
import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;
import com.faizal.simplecommerce.sales.catalog.product.Title;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = CatalogJdbcConfig.class)
class PutProductForSaleTest {

    @Autowired
    private FindProducts findProducts;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void product_put_for_sale_is_found() {
        Product product = new ProductJdbc(
                new ProductId("TEST"),
                new Title("test"),
                new Description("test"),
                new Money(1.f),
                jdbcTemplate
        );
        product.putForSale();

        Product found = findProducts.byId(new ProductId("TEST"));

        assertThat(found.id()).isEqualTo(new ProductId("TEST"));
    }
}
