package com.faizal.simplecommerce.sales.catalog.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.catalog.FindProductsFromCategory;
import com.faizal.simplecommerce.sales.catalog.category.CategoryId;
import com.faizal.simplecommerce.sales.catalog.category.Uri;
import com.faizal.simplecommerce.sales.catalog.jdbc.CatalogJdbcConfig;
import com.faizal.simplecommerce.sales.catalog.jdbc.ProductJdbc;
import com.faizal.simplecommerce.sales.catalog.product.Description;
import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;
import com.faizal.simplecommerce.sales.catalog.product.Title;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = CatalogJdbcConfig.class)
@Sql(statements = {
        "INSERT INTO categories VALUES ('c-1', 'test-category', 'Test');",
        "INSERT INTO products VALUES ('p-1', 'Test', 'Test', 1.00);"
})
class CategorizeProductTest {

    @Autowired
    private FindProductsFromCategory fromCategory;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void categorized_product_is_found_in_the_category() {
        Product product = new ProductJdbc(
                new ProductId("p-1"),
                new Title("Test"),
                new Description("Test"),
                new Money(1.f),
                jdbcTemplate
        );
        product.categorize(new CategoryId("c-1"));

        List<Product> found = fromCategory.byUri(new Uri("test-category")).stream()
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(found).hasSize(1),
                () -> assertThat(found.get(0).id()).isEqualTo(new ProductId("p-1"))
        );
    }
}
