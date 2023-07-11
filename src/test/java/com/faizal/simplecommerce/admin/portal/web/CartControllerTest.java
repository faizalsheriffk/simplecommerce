package com.faizal.simplecommerce.admin.portal.web;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.faizal.simplecommerce.admin.portal.web.CartController;
import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.common.primitives.Quantity;
import com.faizal.simplecommerce.sales.cart.Cart;
import com.faizal.simplecommerce.sales.cart.RetrieveCart;
import com.faizal.simplecommerce.sales.cart.item.CartItem;
import com.faizal.simplecommerce.sales.cart.item.ProductId;
import com.faizal.simplecommerce.sales.cart.item.Title;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveCart retrieveCart;

    @Test
    void index_shows_the_cart_items() throws Exception {
        Cart cart = mock(Cart.class);
        when(cart.items()).thenReturn(List.of(
                new CartItem(new ProductId("test-1"), new Title("Test"), new Money(1.f), new Quantity(123))));
        when(retrieveCart.byId(any())).thenReturn(cart);

        mockMvc.perform(
                get("/cart"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("items", new Object[]{
                        Map.of("id", "test-1",
                               "title", "Test",
                               "price", 1.f * 123,
                               "quantity", 123)}));
    }

    @Test
    void item_is_added_into_the_cart() throws Exception {
        Cart cart = mock(Cart.class);
        when(retrieveCart.byId(any())).thenReturn(cart);

        mockMvc.perform(
                post("/cart")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("productId", "test-1")
                        .param("title", "Test")
                        .param("price", "10.5")
                        .param("quantity", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    void item_is_removed_from_the_cart() throws Exception {
        Cart cart = mock(Cart.class);
        when(retrieveCart.byId(any())).thenReturn(cart);

        mockMvc.perform(
                get("/cart/remove")
                        .param("productId", "test-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }
}
