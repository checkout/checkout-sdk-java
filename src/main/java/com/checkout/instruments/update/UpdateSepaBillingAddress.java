package com.checkout.instruments.update;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing address of the account holder of a SEPA instrument being updated. <p>Every property is required if billing_address is sent. The store shape allows max 35 for city and max 16 for zip.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateSepaBillingAddress {

    /**
     * The first line of the address.
     * [Required]
     * max 200 characters
     */
    private String addressLine1;

    /**
     * The street number. If no number, pass "w/n".
     * [Required]
     * max 10 characters
     */
    private String addressLine2;

    /**
     * The address city.
     * [Required]
     * max 50 characters
     */
    private String city;

    /**
     * The address zip/postal code.
     * [Required]
     * max 50 characters
     */
    private String zip;

    /**
     * The two-letter ISO country code of the address.
     * [Required]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

}
