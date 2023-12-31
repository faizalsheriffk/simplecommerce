package com.faizal.simplecommerce.payments.psp.commercehub.jdbc;

import com.faizal.simplecommerce.payments.psp.commercehub.Address;
import com.faizal.simplecommerce.payments.psp.commercehub.Delivery;
import com.faizal.simplecommerce.payments.psp.commercehub.DeliveryId;
import com.faizal.simplecommerce.payments.psp.commercehub.OrderId;
import com.faizal.simplecommerce.payments.psp.commercehub.Person;
import com.faizal.simplecommerce.payments.psp.commercehub.Place;

import lombok.ToString;

/**
 * Null object implementation for Delivery entity.
 */
@ToString
final class UnknownDelivery implements Delivery {

    @Override
    public DeliveryId id() {
        return new DeliveryId(0);
    }

    @Override
    public OrderId orderId() {
        return new OrderId(0);
    }

    @Override
    public Address address() {
        return new Address(
                new Person("Unknown Person"),
                new Place("Unknown"));
    }

    @Override
    public void prepare() {
        // do nothing
    }

    @Override
    public void dispatch() {
        // do nothing
    }

    @Override
    public boolean isDispatched() {
        return false;
    }
}
