package com.checkout.identities.entities;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The applicant's details.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformation {

    /**
     * The applicant's residence country.
     * [Optional]
     * Standard: ISO 3166-1 alpha-2 country code
     * Pattern: ^[A-Z]{2}
     * Example: FR
     */
    private CountryCode preSelectedResidenceCountry;

    /**
     * The language you want to use for the user interface.
     * [Optional]
     * Format: IETF BCP 47 language tag
     * Example: en-US
     */
    private String preSelectedLanguage;
}
