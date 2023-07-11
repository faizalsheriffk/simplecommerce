package com.faizal.simplecommerce.payments.orchestrator.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.orchestrator.Warehouse;

/**
 * Configuration for JDBC implementation for Warehouse service.
 */
@Configuration
class WarehouseJdbcConfig {

    @Bean
    WarehouseJdbc warehouseJdbc(JdbcTemplate jdbcTemplate) {
        return new WarehouseJdbc(jdbcTemplate);
    }

    @Bean
    GoodsFetchingJdbc goodsFetchingJdbc(Warehouse warehouse, JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new GoodsFetchingJdbc(warehouse, jdbcTemplate, eventPublisher);
    }
}
