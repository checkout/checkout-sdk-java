package com.checkout.accounts;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A citizenship or legal status held by a company representative, as required by the
 * Accounts API v3.0 individual schema.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Citizenship {

    /**
     * The type of citizenship or legal status (for example, {@code citizenship} or {@code residency}).
     */
    private String type;

    /**
     * The two-letter ISO 3166-1 alpha-2 country code.
     */
    private CountryCode country;
}
