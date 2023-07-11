package com.faizal.simplecommerce.admin.portal.web;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.faizal.simplecommerce.admin.portal.web.CatalogController;
import com.faizal.simplecommerce.admin.portal.web.PortalWebConfig;
import com.faizal.simplecommerce.payments.orchestrator.Warehouse;
import com.faizal.simplecommerce.sales.catalog.FindCategories;
import com.faizal.simplecommerce.sales.catalog.FindProducts;
import com.faizal.simplecommerce.sales.catalog.FindProductsFromCategory;
import com.faizal.simplecommerce.sales.catalog.category.Categories;
import com.faizal.simplecommerce.sales.catalog.category.Category;
import com.faizal.simplecommerce.sales.catalog.category.Title;
import com.faizal.simplecommerce.sales.catalog.category.Uri;
import com.faizal.simplecommerce.sales.catalog.product.Products;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
@Import(PortalWebConfig.class)
class WebLayoutAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindCategories findCategories;
    @MockBean
    private FindProducts findProducts;
    @MockBean
    private FindProductsFromCategory findProductsFromCategory;
    @MockBean
    private Warehouse warehouse;

    @Test
    void categories_are_on_every_page() throws Exception {
        Categories categories = testCategories();
        when(findCategories.all()).thenReturn(categories);
        Products products = testProducts();
        when(findProducts.all()).thenReturn(products);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", arrayWithSize(2)));
    }

    private Categories testCategories() {
        Categories categories = mock(Categories.class);
        Category category = mock(Category.class);
        when(category.uri()).thenReturn(new Uri("test"));
        when(category.title()).thenReturn(new Title("Test"));
        when(categories.stream()).thenReturn(Stream.of(category, category));
        return categories;
    }

    private Products testProducts() {
        Products products = mock(Products.class);
        when(products.stream()).thenReturn(Stream.of());
        when(products.range(anyInt())).thenReturn(products);
        return products;
    }
}
