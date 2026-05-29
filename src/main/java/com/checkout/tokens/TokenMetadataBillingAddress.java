package com.checkout.tokens;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Partial billing address — city and country only.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TokenMetadataBillingAddress {

    /**
     * The address city.
     * [Optional]
     */
    private String city;

    /**
     * The billing address country (two-letter ISO code).
     * [Optional]
     * 2 characters
     */
    private String country;
}
