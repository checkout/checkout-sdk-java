package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer;

import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer.phone.Phone;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer.summary.Summary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * customer
 * The customer associated with the payment, if provided in the request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Customer {

    /**
     * The customer's unique identifier. This can be passed as a source when making a payment.
     * [Required]
     * ^(cus)_(\w{26})$
     */
    private String id;

    /**
     * The customer's email address.
     * [Optional]
     */
    private String email;

    /**
     * The customer's name.
     * [Optional]
     */
    private String name;

    /**
     * The customer's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * Summary of the customer's transaction history.  Used for risk assessment when source.type is Tamara
     * [Optional]
     */
    private Summary summary;

}
