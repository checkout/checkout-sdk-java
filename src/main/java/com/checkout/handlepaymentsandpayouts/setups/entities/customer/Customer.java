package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The customer's details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Customer {

    /**
     * Details of the customer's email.
     * [Optional]
     */
    private CustomerEmail email;

    /**
     * The customer's full name.
     * [Optional]
     * max 100 characters
     */
    private String name;

    /**
     * The customer's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * Details of the customer's device.
     * [Optional]
     */
    private CustomerDevice device;

    /**
     * Details of the account the customer holds with the merchant.
     * [Optional]
     */
    private MerchantAccount merchantAccount;

    /**
     * The unique identifier of the customer.
     * [Optional]
     */
    private String id;

    /**
     * The two-letter ISO country code of the customer for this payment.
     * [Optional]
     * min 2 characters, max 2 characters
     */
    private CountryCode country;

    /**
     * The customer's tax identification number.
     * [Optional]
     */
    private String taxNumber;
}
