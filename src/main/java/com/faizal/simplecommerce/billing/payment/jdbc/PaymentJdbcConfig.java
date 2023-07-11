package com.faizal.simplecommerce.billing.payment.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.common.events.EventPublisher;

/**
 * Configuration for JDBC implementation for Payment service.
 */
@Configuration
class PaymentJdbcConfig {

    @Bean
    FindPaymentsJdbc findPaymentsJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new FindPaymentsJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    CollectPaymentJdbc collectPaymentJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new CollectPaymentJdbc(jdbcTemplate, eventPublisher);
    }
}
