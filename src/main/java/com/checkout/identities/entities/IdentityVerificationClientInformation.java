package com.checkout.identities.entities;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * The applicant's details for an identity verification attempt.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityVerificationClientInformation extends ClientInformation {

    /**
     * The country that issued the applicant's identity document.
     * [Optional]
     * Standard: ISO 3166-1 alpha-2 country code
     * Pattern: ^[A-Z]{2}
     * Example: FR
     */
    private CountryCode preSelectedDocumentIssuingCountry;

    /**
     * The type of identity document the applicant uses for the attempt.
     * [Optional]
     */
    private DocumentType preSelectedDocumentType;
}
