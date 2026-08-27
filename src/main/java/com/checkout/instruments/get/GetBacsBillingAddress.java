package com.checkout.instruments.get;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing address of the account holder of a stored Bacs Direct Debit instrument.
 *
 * <p>The retrieve variant of this address declares no length limits other than on the country, so
 * it is deliberately not shared with the store and update variants.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetBacsBillingAddress {

    /**
     * The first line of the address.
     * [Optional]
     */
    private String addressLine1;

    /**
     * The second line of the address.
     * [Optional]
     */
    private String addressLine2;

    /**
     * The address city.
     * [Optional]
     */
    private String city;

    /**
     * The address ZIP or postal code.
     * [Optional]
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
