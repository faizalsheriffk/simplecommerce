package com.faizal.simplecommerce.payments.orchestrator.listeners;

import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.faizal.simplecommerce.payments.orchestrator.Amount;
import com.faizal.simplecommerce.payments.orchestrator.FetchGoods;
import com.faizal.simplecommerce.payments.orchestrator.OrderId;
import com.faizal.simplecommerce.payments.orchestrator.ProductId;
import com.faizal.simplecommerce.payments.orchestrator.ToFetch;
import com.faizal.simplecommerce.sales.order.OrderPlaced;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Warehouse listener for OrderPlaced event.
 */
@Component("warehouse-orderPlacedListener") // a custom name to avoid collision
@RequiredArgsConstructor
class OrderPlacedListener {

    private final @NonNull FetchGoods fetchGoods;

    @TransactionalEventListener
    @Async
    public void on(OrderPlaced event) {
        fetchGoods.fetchFromOrder(
                new OrderId(event.orderId),
                event.items.entrySet().stream()
                        .map(item -> new ToFetch(new ProductId(item.getKey()), new Amount(item.getValue())))
                        .collect(Collectors.toList()));
    }
}
