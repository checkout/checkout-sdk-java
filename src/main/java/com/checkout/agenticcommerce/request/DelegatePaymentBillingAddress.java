package com.checkout.agenticcommerce.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The customer billing address associated with the delegated payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelegatePaymentBillingAddress {

    /**
     * Customer or cardholder full name.
     * <p>
     * [Required]
     * <p>
     * Length: &lt;= 256 characters
     */
    private String name;

    /**
     * First line of the street address.
     * <p>
     * [Required]
     * <p>
     * Length: &lt;= 60 characters
     */
    @SerializedName("line_one")
    private String lineOne;

    /**
     * Second line of the street address.
     * <p>
     * [Optional]
     * <p>
     * Length: &lt;= 60 characters
     */
    @SerializedName("line_two")
    private String lineTwo;

    /**
     * City or locality.
     * <p>
     * [Required]
     * <p>
     * Length: &lt;= 60 characters
     */
    private String city;

    /**
     * State, county, province, or region.
     * <p>
     * [Optional]
     */
    private String state;

    /**
     * Postal or ZIP code.
     * <p>
     * [Required]
     * <p>
     * Length: &lt;= 20 characters
     */
    @SerializedName("postal_code")
    private String postalCode;

    /**
     * ISO 3166-1 alpha-2 country code.
     * <p>
     * [Required]
     * <p>
     * Length: &gt;= 2 characters
     * <p>
     * Length: &lt;= 2 characters
     */
    private String country;
}
