package com.faizal.simplecommerce.admin.portal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.faizal.simplecommerce.payments.psp.commercehub.PrepareDelivery;
import com.faizal.simplecommerce.sales.order.PlaceOrder;

/**
 * Configuration for Portal component.
 */
@Configuration
class PortalConfig {

    @Bean
    PlaceOrderFromCart placeOrderFromCart(PlaceOrder placeOrder) {
        return new PlaceOrderFromCart(placeOrder);
    }

    @Bean
    PrepareOrderDelivery prepareOrderDelivery(PrepareDelivery prepareDelivery) {
        return new PrepareOrderDelivery(prepareDelivery);
    }
}
