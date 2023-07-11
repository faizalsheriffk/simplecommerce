package com.faizal.simplecommerce.admin.portal.web;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.faizal.simplecommerce.common.primitives.Money;
import com.faizal.simplecommerce.common.primitives.Quantity;
import com.faizal.simplecommerce.sales.cart.CartId;
import com.faizal.simplecommerce.sales.cart.RetrieveCart;
import com.faizal.simplecommerce.sales.cart.item.CartItem;
import com.faizal.simplecommerce.sales.cart.item.ProductId;
import com.faizal.simplecommerce.sales.cart.item.Title;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Web controller for Cart use-cases.
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
class CartController {

    private final @NonNull RetrieveCart retrieveCart;

    @GetMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        CartId cartId = new CartIdFromCookies(request, response).cartId();
        model.addAttribute(
                "items",
                retrieveCart.byId(cartId).items().stream()
                        .map(item -> Map.of("id", item.productId().value(),
                                            "title", item.title().value(),
                                            "price", item.total().value(),
                                            "quantity", item.quantity().value()))
                        .toArray());
        return "cart";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@NonNull String productId, @NonNull String title,
                      @NonNull Float price, @NonNull Integer quantity,
                      HttpServletRequest request, HttpServletResponse response) {
        CartId cartId = new CartIdFromCookies(request, response).cartId();
        retrieveCart.byId(cartId).add(new CartItem(
                new ProductId(productId),
                new Title(title),
                new Money(price),
                new Quantity(quantity)));

        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String remove(@NonNull String productId,
                         HttpServletRequest request, HttpServletResponse response) {
        CartId cartId = new CartIdFromCookies(request, response).cartId();
        retrieveCart.byId(cartId).remove(new ProductId(productId));

        return "redirect:/cart";
    }

}
