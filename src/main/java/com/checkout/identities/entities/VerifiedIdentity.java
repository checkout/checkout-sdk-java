package com.checkout.identities.entities;

import com.checkout.common.CountryCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of the applicant's verified identity, extracted from the verified document.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class VerifiedIdentity {

    /**
     * The applicant's full name.
     * [Required]
     * min 2 characters, max 510 characters
     */
    private String fullName;

    /**
     * The applicant's birth date, extracted from the verified document. This is the verified
     * value, not the value the applicant declared.
     * [Required]
     * Format: date (YYYY-MM-DD)
     */
    private String birthDate;

    /**
     * The applicant's first names.
     * [Optional]
     * min 1 character, max 255 characters
     */
    private String firstNames;

    /**
     * The applicant's last name.
     * [Optional]
     * min 1 character, max 255 characters
     */
    private String lastName;

    /**
     * The applicant's last name at birth.
     * [Optional]
     * min 1 character, max 255 characters
     */
    private String lastNameAtBirth;

    /**
     * The applicant's birth place.
     * [Optional]
     * min 1 character, max 255 characters
     */
    private String birthPlace;

    /**
     * The applicant's nationality.
     * [Optional]
     * Standard: ISO 3166-1 alpha-2 country code
     * Pattern: ^[A-Za-z]{2}$
     */
    private CountryCode nationality;

    /**
     * The applicant's gender.
     * [Optional]
     */
    private Gender gender;
}
