package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Verified identity details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class VerifiedIdentity {
    private String fullName;

    private String birthDate;

    private String firstNames;

    private String lastName;

    private String lastNameAtBirth;

    private String birthPlace;

    private String nationality;

    private Gender gender;
}