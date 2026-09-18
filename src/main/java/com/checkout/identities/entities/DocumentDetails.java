package com.checkout.identities.entities;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The applicant's identity document details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DocumentDetails {

    /**
     * The type of identity document.
     * [Required]
     */
    private DocumentType documentType;

    /**
     * The country that issued the document.
     * [Required]
     * Standard: ISO 3166-1 alpha-2 country code
     * Pattern: ^[A-Za-z]{2}$
     */
    private CountryCode documentIssuingCountry;

    /**
     * The pre-signed URL to the captured image of the front of the document.
     * [Required]
     * Format: uri
     */
    private String frontImageSignedUrl;

    /**
     * The applicant's full name, extracted from the verified document.
     * [Required]
     * min 2 characters, max 510 characters
     */
    private String fullName;

    /**
     * The applicant's birth date, extracted from the verified document.
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

    /**
     * The applicant's personal number. This depends on the document type.
     * [Optional]
     * max 255 characters
     */
    private String personalNumber;

    /**
     * The tax identification number (TIN) extracted from the document.
     * [Optional]
     * max 255 characters
     */
    private String taxIdentificationNumber;

    /**
     * The document number extracted from the document.
     * [Optional]
     * max 255 characters
     */
    private String documentNumber;

    /**
     * The document expiry date extracted from the document.
     * [Optional]
     * Format: date (YYYY-MM-DD)
     */
    private String documentExpiryDate;

    /**
     * The document issue date extracted from the document.
     * [Optional]
     * Format: date (YYYY-MM-DD)
     */
    private String documentIssueDate;

    /**
     * The document's place of issue extracted from the document.
     * [Optional]
     * max 255 characters
     */
    private String documentIssuePlace;

    /**
     * The machine-readable zone (MRZ) data extracted from the document.
     * [Optional]
     */
    private String documentMrz;

    /**
     * The address extracted from the document. This is a flat string, not a structured address.
     * [Optional]
     * max 1000 characters
     * Example: 123 Main Street, London, SW1A 1AA
     */
    private String address;

    /**
     * The date the residence permit was obtained, extracted from the document.
     * [Optional]
     * Format: date (YYYY-MM-DD)
     */
    private String permitObtainingDate;

    /**
     * The residence permit expiry date extracted from the document.
     * [Optional]
     * Format: date (YYYY-MM-DD)
     */
    private String permitExpiryDate;

    /**
     * The detailed residence permit type extracted from the document.
     * [Optional]
     * max 255 characters
     */
    private String permitTypeDetailed;

    /**
     * The residence permit remarks extracted from the document.
     * [Optional]
     * max 255 characters
     */
    private String permitTypeRemarks;

    /**
     * The pre-signed URL to the captured image of the back of the document.
     * [Optional]
     * Format: uri
     */
    private String backImageSignedUrl;

    /**
     * The pre-signed URL to the captured image of the signature.
     * [Optional]
     * Format: uri
     */
    private String signatureImageSignedUrl;
}
