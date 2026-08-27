package com.checkout.instruments.create;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing address of the account holder of a Bacs Direct Debit instrument being stored.
 *
 * <p>The length constraints differ from the update and retrieve variants of the same address, so
 * this type is deliberately not shared with them.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateBacsBillingAddress {

    /**
     * The first line of the address.
     * [Optional]
     * max 200 characters
     */
    private String addressLine1;

    /**
     * The street number. If no number, pass "w/n".
     * [Optional]
     * max 10 characters
     */
    private String addressLine2;

    /**
     * The address city.
     * [Optional]
     * max 35 characters
     */
    private String city;

    /**
     * The address zip/postal code.
     * [Optional]
     * max 16 characters
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
