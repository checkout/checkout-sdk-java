package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.identification;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * identification
 * The account holder's identification.
 * Providing identification details for the unreferenced refund recipient increases the likelihood of a successful
 * unreferenced refund.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Identification {

    /**
     * The type of identification for the account holder.
     * [Required]
     */
    private Type type;

    /**
     * The account holder's identification number.
     * [Required]
     * &lt;= 25
     */
    private String number;

    /**
     * If applicable, the country that issued the account holder's identification, as a two-letter ISO country code.
     * Providing issuing_country increases the likelihood of a successful identity verification.
     * [Optional]
     * 2 characters
     */
    @SerializedName("issuing_country")
    private String issuingCountry;

    /**
     * If applicable, the expiration date of the account holder's identification, in the format YYYY-MM-DD.
     * Providing date_of_expiry increases the likelihood of a successful identity verification.
     * [Optional]
     * 10 characters
     */
    @SerializedName("date_of_expiry")
    private String dateOfExpiry;

}
