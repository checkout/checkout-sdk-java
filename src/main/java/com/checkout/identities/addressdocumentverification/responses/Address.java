package com.checkout.identities.addressdocumentverification.responses;

import lombok.Data;

/**
 * The address extracted from the document.
 */
@Data
public final class Address {

    /**
     * The first line of the address.
     * max 250 characters
     */
    private String addressLine1;

    /**
     * The second line of the address.
     * max 250 characters
     */
    private String addressLine2;

    /**
     * The city or town.
     * max 50 characters
     */
    private String city;

    /**
     * The state, county, or province.
     * max 50 characters
     */
    private String state;

    /**
     * The postal or ZIP code.
     * max 50 characters
     */
    private String zip;

    /**
     * The two-letter ISO country code of the address.
     * max 2 characters
     */
    private String country;
}
