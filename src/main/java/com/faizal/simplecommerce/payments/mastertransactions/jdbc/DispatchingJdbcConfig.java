package com.faizal.simplecommerce.payments.mastertransactions.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.mastertransactions.Dispatching;

/**
 * Configuration for Dispatching JDBC.
 */
@Configuration
class DispatchingJdbcConfig {

    @Bean
    DispatchingSagaJdbc dispatchingSagaJdbc(Dispatching dispatching, JdbcTemplate jdbcTemplate) {
        return new DispatchingSagaJdbc(dispatching, jdbcTemplate);
    }

    @Bean
    DispatchingJdbc dispatchingJdbc(EventPublisher eventPublisher) {
        return new DispatchingJdbc(eventPublisher);
    }
}
