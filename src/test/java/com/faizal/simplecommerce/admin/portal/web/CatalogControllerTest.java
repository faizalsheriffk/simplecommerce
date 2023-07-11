package com.faizal.simplecommerce.admin.portal.web;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.faizal.simplecommerce.admin.portal.web.CatalogController;
import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.payments.orchestrator.Amount;
import com.faizal.simplecommerce.payments.orchestrator.InStock;
import com.faizal.simplecommerce.payments.orchestrator.Warehouse;
import com.faizal.simplecommerce.sales.catalog.FindProducts;
import com.faizal.simplecommerce.sales.catalog.FindProductsFromCategory;
import com.faizal.simplecommerce.sales.catalog.category.Uri;
import com.faizal.simplecommerce.sales.catalog.product.Description;
import com.faizal.simplecommerce.sales.catalog.product.Product;
import com.faizal.simplecommerce.sales.catalog.product.ProductId;
import com.faizal.simplecommerce.sales.catalog.product.Products;
import com.faizal.simplecommerce.sales.catalog.product.Title;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindProducts findProducts;
    @MockBean
    private FindProductsFromCategory findProductsFromCategory;
    @MockBean
    private Warehouse warehouse;

    @Test
    void index_has_products() throws Exception {
        Products products = testProducts();
        when(findProducts.all()).thenReturn(products);
        when(warehouse.leftInStock(any())).thenReturn(new InStock(new Amount(1)));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", arrayWithSize(2)));
    }

    @Test
    void category_has_products() throws Exception {
        Products products = testProducts();
        when(findProductsFromCategory.byUri(new Uri("test-category"))).thenReturn(products);
        when(warehouse.leftInStock(any())).thenReturn(new InStock(new Amount(1)));

        mockMvc.perform(get("/category/test-category"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", arrayWithSize(2)));
    }

    private Products testProducts() {
        Product product = mock(Product.class);
        when(product.id()).thenReturn(new ProductId("TEST"));
        when(product.title()).thenReturn(new Title("Test"));
        when(product.description()).thenReturn(new Description("Test"));
        when(product.price()).thenReturn(new Money(1.f));
        Products products = mock(Products.class);
        when(products.stream()).thenReturn(Stream.of(product, product));
        when(products.range(anyInt())).thenReturn(products);
        return products;
    }
}
