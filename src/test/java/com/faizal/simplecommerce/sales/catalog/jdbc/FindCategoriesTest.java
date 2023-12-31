package com.faizal.simplecommerce.sales.catalog.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.faizal.simplecommerce.sales.catalog.FindCategories;
import com.faizal.simplecommerce.sales.catalog.category.Category;
import com.faizal.simplecommerce.sales.catalog.category.CategoryId;
import com.faizal.simplecommerce.sales.catalog.category.Title;
import com.faizal.simplecommerce.sales.catalog.category.Uri;
import com.faizal.simplecommerce.sales.catalog.jdbc.CatalogJdbcConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = CatalogJdbcConfig.class)
@Sql(statements = "INSERT INTO categories VALUES ('1', 'cat1', 'Cat 1'), (2, 'cat2', 'Cat 2');")
class FindCategoriesTest {

    @Autowired
    private FindCategories findCategories;

    @Test
    void all_categories_are_found() {
        List<CategoryId> categoryIds = findCategories.all().stream()
                .map(Category::id)
                .collect(Collectors.toList());

        assertThat(categoryIds).containsExactlyInAnyOrder(
                new CategoryId(1), new CategoryId(2));
    }

    @Test
    void category_by_id_is_found() {
        Category category = findCategories.byId(new CategoryId(1));
        assertAll(
                () -> assertThat(category.id()).isEqualTo(new CategoryId(1)),
                () -> assertThat(category.uri()).isEqualTo(new Uri("cat1")),
                () -> assertThat(category.title()).isEqualTo(new Title("Cat 1"))
        );
    }

    @Test
    void unknown_category_found_for_unknown_id() {
        Category category = findCategories.byId(new CategoryId("does not exist"));

        assertThat(category.id()).isEqualTo(new CategoryId(0));
    }
}
