package com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource.accountholder.billingaddress;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * billing_address
 * Address of the account holder.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillingAddress {

    /**
     * Postal code of the account holder.
     * [Required]
     */
    private String zip;

    /**
     * City of the account holder.
     * [Required]
     */
    private String city;

    /**
     * ISO 3166 alpha-2 account holder country code.
     * [Required]
     */
    private CountryCode country;

    /**
     * Street address of the account holder.
     * [Optional]
     */
    @SerializedName("address_line1")
    private String addressLineOne;

    /**
     * Street address of the account holder.
     * [Optional]
     */
    @SerializedName("address_line2")
    private String addressLineTwo;

}
