package com.faizal.simplecommerce.billing.payment.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.faizal.simplecommerce.billing.payment.CollectPayment;
import com.faizal.simplecommerce.billing.payment.Payment;
import com.faizal.simplecommerce.billing.payment.ReferenceId;
import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.common.primitives.Money;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC implementation for Collect Payment use-cases.
 */
@RequiredArgsConstructor
class CollectPaymentJdbc implements CollectPayment {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void collect(ReferenceId referenceId, Money total) {
        Payment payment = new PaymentJdbc(referenceId, total, jdbcTemplate, eventPublisher);
        payment.request();

        // here an external service like PayPal or Visa is called...

        payment.collect();
    }
}
