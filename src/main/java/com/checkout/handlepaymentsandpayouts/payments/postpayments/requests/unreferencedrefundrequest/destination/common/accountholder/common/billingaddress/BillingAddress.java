package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.billingaddress;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * billing_address
 * The account holder's billing address.
 * If your company is incorporated in the United States, this field is required for all unreferenced refunds you perform.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillingAddress {

    /**
     * The first line of the address.
     * [Optional]
     * <= 200
     */
    @SerializedName("address_line1")
    private String addressLine1;

    /**
     * The second line of the address.
     * [Optional]
     * <= 200
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
     * The address state.
     * [Optional]
     * [ 2 .. 3 ] characters
     */
    private String state;

    /**
     * The address ZIP or postal code.
     * You must provide United States ZIPs in the format 00000, or 00000-0000.
     * [Optional]
     * &lt;= 10
     */
    private String zip;

    /**
     * The address country, as a two-letter ISO country code.
     * [Optional]
     * 2 characters
     */
    private CountryCode country;

}
