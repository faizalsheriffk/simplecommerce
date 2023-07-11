package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.psp.commercehub.FindDeliveries;

/**
 * Configuration for JDBC implementation for Delivery service.
 */
@Configuration
class DeliveryJdbcConfig {

    @Bean
    FindDeliveriesJdbc findDeliveriesJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new FindDeliveriesJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    PrepareDeliveryJdbc prepareDeliveryJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new PrepareDeliveryJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    DispatchDeliveryJdbc dispatchDeliveryJdbc(FindDeliveries findDeliveries) {
        return new DispatchDeliveryJdbc(findDeliveries);
    }
}
