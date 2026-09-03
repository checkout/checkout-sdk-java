package com.checkout.payments.request.source.apm;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder's billing address on a SEPA payment source.
 *
 * <p>Every property is required. Deliberately not {@link com.checkout.common.Address}, which also
 * declares a state that this position does not accept.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RequestSepaBillingAddress {

    /**
     * The account holder's street name.
     * [Required]
     */
    private String addressLine1;

    /**
     * The account holder's street number.
     * [Required]
     * max 10 characters
     */
    private String addressLine2;

    /**
     * The account holder's city.
     * [Required]
     * max 35 characters
     */
    private String city;

    /**
     * The account holder's zip code.
     * [Required]
     * max 16 characters
     */
    private String zip;

    /**
     * The account holder's country, as an ISO 3166-1 alpha-2 code.
     * [Required]
     * max 2 characters
     */
    private CountryCode country;

}
