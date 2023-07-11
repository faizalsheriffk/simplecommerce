package com.faizal.simplecommerce.sales.catalog;

import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;
import com.faizal.simplecommerce.sales.catalog.product.Products;

/**
 * Find Products use-case.
 */
public interface FindProducts {

    /**
     * Lists all products.
     *
     * @return all products
     */
    Products all();

    /**
     * Finds a product by ID.
     *
     * @param id the product ID
     * @return the found product
     */
    Product byId(ProductId id);
}
