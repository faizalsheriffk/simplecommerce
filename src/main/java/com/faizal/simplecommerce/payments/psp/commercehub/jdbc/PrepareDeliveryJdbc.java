package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.psp.commercehub.Address;
import com.faizal.simplecommerce.payments.psp.commercehub.Delivery;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;
import com.faizal.simplecommerce.payments.psp.commercehub.PrepareDelivery;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC implementation for Prepare Delivery use-cases.
 */
@RequiredArgsConstructor
@Slf4j
class PrepareDeliveryJdbc implements PrepareDelivery {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Transactional
    @Override
    public void prepare(@NonNull OrderId orderId, @NonNull Address address) {
        Delivery delivery = new DeliveryJdbc(orderId, address, jdbcTemplate, eventPublisher);
        delivery.prepare();
    }
}
