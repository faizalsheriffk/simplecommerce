package com.faizal.simplecommerce.payments.psp.commercehub.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faizal.simplecommerce.payments.psp.commercehub.Delivery;
import com.faizal.simplecommerce.payments.psp.commercehub.FindDeliveries;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for Delivery use-cases.
 */
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
class DeliveryController {

    private final @NonNull FindDeliveries findDeliveries;

    @GetMapping
    public List<Map<String, ?>> all() {
        return findDeliveries.all().stream()
                .map(delivery -> Map.of(
                        "id", delivery.id().value(),
                        "orderId", delivery.orderId().value()))
                .collect(Collectors.toList());
    }

    @GetMapping("/order/{orderId}")
    public Map<String, ?> byOrder(@PathVariable @NonNull Object orderId) {
        Delivery delivery = findDeliveries.byOrder(new OrderId(orderId));
        return Map.of(
                "id", delivery.id().value(),
                "address", Map.of(
                        "person", delivery.address().person().value(),
                        "place", delivery.address().place().value()),
                "dispatched", delivery.isDispatched());
    }
}
