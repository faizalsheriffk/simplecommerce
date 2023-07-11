package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryId;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryInfo;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryInfos;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC implementation of Delivery Infos collection.
 */
@RequiredArgsConstructor
final class DeliveryInfosJdbc implements DeliveryInfos {

    private final @NonNull String query;

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Stream<DeliveryInfo> stream() {
        return jdbcTemplate.queryForList(query.concat(" ORDER BY 1"))
                .stream()
                .map(this::toDeliveryInfo);
    }

    private DeliveryInfo toDeliveryInfo(Map<String, Object> entry) {
        return new DeliveryInfo(
                new DeliveryId(entry.get("id")),
                new OrderId(entry.get("orderId")));
    }
}
