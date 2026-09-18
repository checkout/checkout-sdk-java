package com.checkout.identities.entities;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The applicant's address.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IdvAddress {

    /**
     * The first line of the address.
     * [Optional]
     * max 250 characters
     */
    private String addressLine1;

    /**
     * The second line of the address.
     * [Optional]
     * max 250 characters
     */
    private String addressLine2;

    /**
     * The city or town.
     * [Optional]
     * max 50 characters
     */
    private String city;

    /**
     * The state, county, or province.
     * [Optional]
     * max 50 characters
     */
    private String state;

    /**
     * The postal or ZIP code.
     * [Optional]
     * max 50 characters
     */
    private String zip;

    /**
     * The two-letter ISO country code of the address.
     * [Optional]
     * Standard: ISO 3166-1 alpha-2 country code
     * max 2 characters
     */
    private CountryCode country;

}
