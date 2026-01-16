package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer information for payment setup
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    /**
     * The customer's email information
     */
    private CustomerEmail email;

    /**
     * &lt;= 100 characters
     * The customer's full name
     */
    private String name;

    /**
     * The customer's phone number
     */
    private Phone phone;

    /**
     * Information about the customer's device
     */
    private CustomerDevice device;

    /**
     * Details of the account the customer holds with the merchant
     */
    @SerializedName("merchant_account")
    private MerchantAccount merchantAccount;
}