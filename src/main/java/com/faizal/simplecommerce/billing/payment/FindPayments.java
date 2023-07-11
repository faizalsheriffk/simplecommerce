package com.faizal.simplecommerce.billing.payment;

/**
 * Find Payments use-case.
 */
public interface FindPayments {

    /**
     * Finds all payments.
     *
     * @return all payments
     */
    Payments all();
}
