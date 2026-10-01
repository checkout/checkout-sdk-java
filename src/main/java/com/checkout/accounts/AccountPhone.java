package com.checkout.accounts;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A phone number on the Accounts API: the sub-entity's contact phone, or a representative's phone.
 * See {@link ContactDetails} for the per-variant number format.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountPhone {

    /**
     * The ISO 3166-1 alpha-2 country where the number is registered, not the dialling code.
     * [Required] on Accounts API v3.0; not part of the v2.0 schemas.
     */
    private CountryCode countryCode;

    /**
     * The phone number, without the country calling code.
     * [Required]
     */
    private String number;

}
