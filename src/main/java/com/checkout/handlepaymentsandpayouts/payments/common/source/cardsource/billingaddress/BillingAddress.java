package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.billingaddress;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * billing_address
 * The payment source owner's billing address
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillingAddress {

    /**
     * The first line of the address.
     * [Optional]
     * &lt;= 200
     */
    @SerializedName("address_line1")
    private String addressLine1;

    /**
     * The second line of the address
     * [Optional]
     * &lt;= 200
     */
    @SerializedName("address_line2")
    private String addressLine2;

    /**
     * The address city.
     * [Optional]
     * &lt;= 50
     */
    private String city;

    /**
     * The state or province of the address country ISO 3166-2 code (for example: CA for California in the United
     * States).
     * [Optional]
     * &lt;= 3
     */
    private String state;

    /**
     * The address zip or postal code.
     * [Optional]
     * &lt;= 50
     */
    private String zip;

    /**
     * The two-letter ISO country code of the address.
     * [Optional]
     * 2 characters
     */
    private CountryCode country;

}
