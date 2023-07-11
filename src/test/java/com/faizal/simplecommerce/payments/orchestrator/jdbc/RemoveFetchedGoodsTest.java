package com.faizal.simplecommerce.payments.orchestrator.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.faizal.simplecommerce.common.events.EventPublisher;
import com.faizal.simplecommerce.payments.orchestrator.RemoveFetchedGoods;
import com.faizal.simplecommerce.payments.orchestrator.jdbc.WarehouseJdbcConfig;

@JdbcTest
@ContextConfiguration(classes = WarehouseJdbcConfig.class)
class RemoveFetchedGoodsTest {

    @Autowired
    private RemoveFetchedGoods removeFetchedGoods;

    @MockBean
    private EventPublisher eventPublisher;

    // TODO
}
