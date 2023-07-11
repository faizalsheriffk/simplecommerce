package com.faizal.simplecommerce.sales.catalog.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.faizal.simplecommerce.sales.catalog.FindProductsFromCategory;
import com.faizal.simplecommerce.sales.catalog.category.Uri;
import com.faizal.simplecommerce.sales.catalog.jdbc.CatalogJdbcConfig;
import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = CatalogJdbcConfig.class)
@Sql("/test-data-sales-find-products.sql")
class FindProductsFromCategoryTest {

    @Autowired
    private FindProductsFromCategory fromCategory;

    @Test
    void products_from_a_category_by_uri_are_found() {
        List<ProductId> productIds = fromCategory.byUri(new Uri("cat1")).stream()
                .map(Product::id)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactlyInAnyOrder(
                new ProductId("p-1"), new ProductId("p-2"));
    }
}
