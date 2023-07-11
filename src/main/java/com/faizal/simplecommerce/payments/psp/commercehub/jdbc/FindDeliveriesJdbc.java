package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.psp.commercehub.Address;
import com.faizal.simplecommerce.payments.psp.commercehub.Delivery;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryId;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryInfos;
import com.faizal.simplecommerce.payments.psp.commercehub.FindDeliveries;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;
import com.faizal.simplecommerce.payments.psp.commercehub.Person;
import com.faizal.simplecommerce.payments.psp.commercehub.Place;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC implementation for Delivery use-cases.
 */
@RequiredArgsConstructor
@Slf4j
class FindDeliveriesJdbc implements FindDeliveries {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public DeliveryInfos all() {
        return new DeliveryInfosJdbc(
                "SELECT id, order_id orderId FROM deliveries", jdbcTemplate);
    }

    @Transactional
    @Override
    public Delivery byOrder(OrderId orderId) {
        var delivery = jdbcTemplate.queryForList(
                "SELECT id, order_id orderId, person, place, dd.delivery_id dispatched FROM deliveries " +
                "LEFT JOIN dispatched_deliveries dd ON id = dd.delivery_id " +
                "WHERE order_id = ?", orderId.value())
                .stream().findAny();

        return delivery
                .map(this::toDelivery)
                .orElseGet(UnknownDelivery::new);
    }

    @Override
    public boolean isPrepared(OrderId orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(order_id) FROM deliveries " +
                "WHERE order_id = ?", Integer.class, orderId.value()) > 0;
    }

    private Delivery toDelivery(Map<String, Object> delivery) {
        return new DeliveryJdbc(
                new DeliveryId(delivery.get("id")),
                new OrderId(delivery.get("orderId")),
                new Address(
                        new Person((String) delivery.get("person")),
                        new Place((String) delivery.get("place"))),
                delivery.get("dispatched") != null,
                jdbcTemplate, eventPublisher);
    }
}
