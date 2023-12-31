package com.faizal.simplecommerce.admin.portal.web;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import com.faizal.simplecommerce.admin.portal.PlaceOrderFromCart;
import com.faizal.simplecommerce.admin.portal.PrepareOrderDelivery;
import com.faizal.simplecommerce.admin.portal.web.OrderController;
import com.faizal.simplecommerce.payments.psp.commercehub.Address;
import com.faizal.simplecommerce.payments.psp.commercehub.Person;
import com.faizal.simplecommerce.payments.psp.commercehub.Place;
import com.faizal.simplecommerce.sales.cart.Cart;
import com.faizal.simplecommerce.sales.cart.RetrieveCart;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceOrderFromCart placeOrderFromCart;
    @MockBean
    private PrepareOrderDelivery prepareOrderDelivery;
    @MockBean
    private RetrieveCart retrieveCart;

    @Test
    void index_shows_the_order_form_with_name_and_address_input_fields() throws Exception {
        mockMvc.perform(
                get("/order"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<form")))
                .andExpect(content().string(containsString("action=\"/order\"")))
                .andExpect(content().string(containsString("method=\"post\"")))
                .andExpect(content().string(containsString("name=\"name\"")))
                .andExpect(content().string(containsString("name=\"address\"")));
    }

    @Test
    void order_is_placed() throws Exception {
        Cart cart = mock(Cart.class);
        when(retrieveCart.byId(any())).thenReturn(cart);

        mockMvc.perform(
                post("/order")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "Test Name")
                        .param("address", "Test Address 123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/success"));

        verify(placeOrderFromCart).placeOrder(any(UUID.class), any(Cart.class));
    }

    @Test
    void delivery_is_prepared() throws Exception {
        Cart cart = mock(Cart.class);
        when(retrieveCart.byId(any())).thenReturn(cart);

        mockMvc.perform(
                post("/order")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "Test Name")
                        .param("address", "Test Address 123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/success"));

        verify(prepareOrderDelivery).prepareDelivery(any(),
                eq(new Address(new Person("Test Name"), new Place("Test Address 123"))));
    }

    @Test
    void order_form_is_not_filled() throws Exception {
        mockMvc.perform(
                post("/order").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/error?message=requires"));
    }

    @Test
    void success_is_shown() throws Exception {
        mockMvc.perform(get("/order/success"))
                .andExpect(status().isOk());
    }

    @Test
    void error_message_is_shown() throws Exception {
        mockMvc.perform(get("/order/error")
                                .param("message", "testmessage"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("messageCode", "testmessage"));
    }

    @Test
    void error_is_shown_for_no_items() throws Exception {
        Cart cart = mock(Cart.class);
        when(retrieveCart.byId(any())).thenReturn(cart);

        doThrow(mock(PlaceOrderFromCart.NoItemsToOrderException.class))
                .when(placeOrderFromCart).placeOrder(any(), any(Cart.class));

        mockMvc.perform(
                post("/order")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("name", "Test Name")
                        .param("address", "Test Address 123")
                        .requestAttr("request", new MockHttpServletRequest()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/error?message=noitems"));
    }
}
