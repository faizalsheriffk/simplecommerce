package com.faizal.simplecommerce.payments.mastertransactions.jdbc;

import java.time.Instant;

import org.springframework.transaction.annotation.Transactional;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.mastertransactions.DeliveryDispatched;
import com.faizal.simplecommerce.payments.mastertransactions.Dispatching;
import com.faizal.simplecommerce.payments.mastertransactions.OrderId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DispatchingJdbc implements Dispatching {

    private final @NonNull EventPublisher eventPublisher;

    @Transactional
    public void dispatch(OrderId orderId) {

        // do the actual dispatching...

        log.info("Order {} is being dispatched.", orderId);

        eventPublisher.raise(new DeliveryDispatched(Instant.now(), orderId.value()));
    }
}
