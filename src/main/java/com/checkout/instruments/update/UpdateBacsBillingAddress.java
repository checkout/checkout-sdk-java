package com.checkout.instruments.update;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing address of the account holder of a Bacs Direct Debit instrument being updated.
 *
 * <p>The city and zip limits are wider here than on the store request, so this type is
 * deliberately not shared with it.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateBacsBillingAddress {

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
     * max 50 characters
     */
    private String city;

    /**
     * The address zip/postal code.
     * [Optional]
     * max 50 characters
     */
    private String zip;

    /**
     * The two-letter ISO country code of the address.
     * [Optional]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

}
