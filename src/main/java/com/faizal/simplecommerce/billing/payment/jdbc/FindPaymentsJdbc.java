package com.faizal.simplecommerce.billing.payment.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.billing.payment.FindPayments;
import com.faizal.simplecommerce.billing.payment.Payments;
import com.faizal.simplecommerce.common.events.EventPublisher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC implementation for Find Payments use-cases.
 */
@RequiredArgsConstructor
final class FindPaymentsJdbc implements FindPayments {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public Payments all() {
        return new PaymentsJdbc(
                "SELECT id, reference_id referenceId, total, status FROM payments",
                jdbcTemplate, eventPublisher);
    }
}
