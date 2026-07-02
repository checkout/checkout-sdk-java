package com.checkout.handlepaymentsandpayouts.setups.entities.billingDescriptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing descriptor for the payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupBillingDescriptor {

    /**
     * A dynamic description of the payment.
     * [Optional]
     * max 25 characters
     */
    private String name;

    /**
     * The city from which the payment was made.
     * [Optional]
     * max 13 characters
     */
    private String city;

    /**
     * The reference shown on the statement.
     * [Optional]
     * max 50 characters
     */
    private String reference;
}
