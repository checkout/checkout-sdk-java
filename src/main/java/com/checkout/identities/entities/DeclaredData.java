package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The personal details provided by the applicant.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DeclaredData {

    /**
     * The applicant's name.
     * [Required]
     * min 2 characters, max 255 characters
     * Example: Hannah Bret
     */
    private String name;

    /**
     * The applicant's birth date, as provided by the applicant. This is the declared value, not
     * the value extracted from the verified document.
     * [Optional]
     * Format: date (YYYY-MM-DD)
     * Example: 1994-10-15
     */
    private String birthDate;
}
