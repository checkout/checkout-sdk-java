package com.checkout.instruments.get;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing address of the account holder of a stored SEPA instrument.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetSepaBillingAddress {

    /**
     * The first line of the address.
     * [Required]
     */
    private String addressLine1;

    /**
     * The second line of the address.
     * [Required]
     */
    private String addressLine2;

    /**
     * The address city.
     * [Required]
     */
    private String city;

    /**
     * The address ZIP or postal code.
     * [Required]
     */
    private String zip;

    /**
     * The address country.
     * [Required]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

}
