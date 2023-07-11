package com.faizal.simplecommerce.payments.orchestrator.listeners;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.faizal.simplecommerce.payments.mastertransactions.DeliveryDispatched;
import com.faizal.simplecommerce.payments.orchestrator.Amount;
import com.faizal.simplecommerce.payments.orchestrator.FetchGoods;
import com.faizal.simplecommerce.payments.orchestrator.OrderId;
import com.faizal.simplecommerce.payments.orchestrator.ProductId;
import com.faizal.simplecommerce.payments.orchestrator.RemoveFetchedGoods;
import com.faizal.simplecommerce.payments.orchestrator.ToFetch;
import com.faizal.simplecommerce.payments.orchestrator.listeners.DeliveryDispatchedListener;
import com.faizal.simplecommerce.payments.orchestrator.listeners.OrderPlacedListener;
import com.faizal.simplecommerce.sales.order.OrderPlaced;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WarehouseListenersTest {

    @Test
    void on_order_placed_fetches_goods() {
        FetchGoods fetchGoods = mock(FetchGoods.class);
        OrderPlacedListener listener = new OrderPlacedListener(fetchGoods);

        listener.on(new OrderPlaced(Instant.now(), "TEST123", Map.of("test-1", 2), 246.f));

        verify(fetchGoods).fetchFromOrder(
                new OrderId("TEST123"),
                List.of(new ToFetch(new ProductId("test-1"), new Amount(2))));
    }

    @Test
    void on_delivery_dispatched_removes_fetched_goods() {
        RemoveFetchedGoods removeFetchedGoods = mock(RemoveFetchedGoods.class);
        DeliveryDispatchedListener listener = new DeliveryDispatchedListener(removeFetchedGoods);

        listener.on(new DeliveryDispatched(Instant.now(), "TEST123"));

        verify(removeFetchedGoods).removeForOrder(new OrderId("TEST123"));
    }
}
