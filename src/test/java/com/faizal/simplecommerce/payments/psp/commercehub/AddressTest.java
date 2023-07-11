package com.faizal.simplecommerce.payments.psp.commercehub;

import org.junit.jupiter.api.Test;

import com.faizal.simplecommerce.payments.psp.commercehub.Address;
import com.faizal.simplecommerce.payments.psp.commercehub.Person;
import com.faizal.simplecommerce.payments.psp.commercehub.Place;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AddressTest {

    @Test
    void address_values() {
        Person person = new Person("Test Person");
        Place place = new Place("Test Address 123");
        Address address = new Address(person, place);

        assertAll(
                () -> assertThat(address.person()).isEqualTo(person),
                () -> assertThat(address.place()).isEqualTo(place)
        );
    }
}
