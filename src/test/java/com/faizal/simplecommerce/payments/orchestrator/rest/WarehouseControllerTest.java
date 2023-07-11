package com.faizal.simplecommerce.payments.orchestrator.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.faizal.simplecommerce.payments.orchestrator.Amount;
import com.faizal.simplecommerce.payments.orchestrator.InStock;
import com.faizal.simplecommerce.payments.orchestrator.ProductId;
import com.faizal.simplecommerce.payments.orchestrator.Warehouse;
import com.faizal.simplecommerce.payments.orchestrator.rest.WarehouseController;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseController.class)
class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Warehouse warehouse;

    @Test
    void left_in_stock() throws Exception {
        when(warehouse.leftInStock(eq(new ProductId("test-123")))).thenReturn(new InStock(new Amount(456)));

        mockMvc.perform(get("/warehouse/stock/test-123"))
                .andExpect(status().isOk())
                .andExpect(content().string("456"));
    }
}
