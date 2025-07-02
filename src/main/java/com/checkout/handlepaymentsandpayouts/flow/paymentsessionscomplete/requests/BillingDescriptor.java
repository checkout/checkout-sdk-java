package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillingDescriptor {

    /**
     * A description for the payment, which will be displayed on the customer's card statement. Only applicable for card
     * payments.
     */
    private String name;

    /**
     * The city from which the payment originated. Only applicable for card payments.
     */
    private String city;

    /**
     * The reference to display on the customer's bank statement. Required for payouts to bank accounts.
     */
    private String reference;

}
