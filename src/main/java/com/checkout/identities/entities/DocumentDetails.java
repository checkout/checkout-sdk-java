package com.checkout.identities.entities;

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

    private DocumentType documentType;

    private String documentIssuingCountry;

    private String frontImageSignedUrl;

    private String fullName;

    private String birthDate;

    private String firstNames;

    private String lastName;

    private String lastNameAtBirth;

    private String birthPlace;

    private String nationality;

    private Gender gender;

    private String personalNumber;

    private String taxIdentificationNumber;

    private String documentNumber;

    private String documentExpiryDate;

    private String documentIssueDate;

    private String documentIssuePlace;

    private String documentMrz;

    private String backImageSignedUrl;

    private String signatureImageSignedUrl;
}