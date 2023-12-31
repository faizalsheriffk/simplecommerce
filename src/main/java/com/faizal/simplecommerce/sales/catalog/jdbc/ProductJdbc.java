package com.faizal.simplecommerce.sales.catalog.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.sales.catalog.category.CategoryId;
import com.faizal.simplecommerce.sales.catalog.product.Description;
import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;
import com.faizal.simplecommerce.sales.catalog.product.Title;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * JDBC implementation for Product entity.
 */
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "title", "price"})
final class ProductJdbc implements Product {

    private final @NonNull ProductId id;

    private @NonNull Title title;
    private @NonNull Description description;
    private @NonNull Money price;

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public ProductId id() {
        return id;
    }

    @Override
    public Title title() {
        return title;
    }

    @Override
    public Description description() {
        return description;
    }

    @Override
    public Money price() {
        return price;
    }

    @Override
    public void changeTitle(Title title) {
        this.title = title;
        jdbcTemplate.update("UPDATE products SET title = ? WHERE id = ?",
                            title.value(), id.value());
    }

    @Override
    public void changeDescription(Description description) {
        this.description = description;
        jdbcTemplate.update("UPDATE products SET description = ? WHERE id = ?",
                            description.value(), id.value());
    }

    @Override
    public void changePrice(Money price) {
        this.price = price;
        jdbcTemplate.update("UPDATE products SET price = ? WHERE id = ?",
                            price.value(), id.value());
    }

    @Override
    public void putForSale() {
        jdbcTemplate.update("INSERT INTO products VALUES(?, ?, ?, ?)",
                            id.value(), title.value(), description.value(), price.value());
    }

    @Override
    public void categorize(CategoryId categoryId) {
        jdbcTemplate.update("INSERT INTO products_in_categories VALUES(?, ?)",
                            id.value(), categoryId.value());
    }
}
