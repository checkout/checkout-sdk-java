package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * The personal details provided by the applicant for an identity verification.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDeclaredData extends DeclaredData {

    /**
     * The applicant's mobile phone number, if sharing the attempt URL via SMS.
     * [Optional]
     */
    private PhoneNumber phoneNumber;

    /**
     * The applicant's email address.
     * [Optional]
     * Format: email
     * Example: hannah.bret@example.com
     */
    private String email;

    /**
     * The applicant's address.
     * [Optional]
     */
    private IdvAddress address;
}
