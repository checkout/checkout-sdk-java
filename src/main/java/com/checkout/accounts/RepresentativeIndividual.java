package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The personal details of a company representative ({@code company.representatives[].individual}),
 * Accounts API v3.0.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RepresentativeIndividual {

    /**
     * The representative's first name.
     * [Required]
     * min 2 characters, max 50 characters
     */
    private String firstName;

    /**
     * The representative's middle name. Required if it appears in official documents.
     * [Optional]
     * min 2 characters, max 50 characters
     */
    private String middleName;

    /**
     * The representative's last name.
     * [Required]
     * min 2 characters, max 50 characters
     */
    private String lastName;

    /**
     * The date of birth of the person according to the Gregorian calendar.
     * [Required]
     */
    private DateOfBirth dateOfBirth;

    /**
     * The place of birth of the person.
     * [Required]
     */
    private PlaceOfBirth placeOfBirth;

    /**
     * The list of citizenships or legal statuses for the representative.
     * [Required] for the US ISV Seller variants only; not part of the other v3.0 schemas, leave unset
     * for them.
     */
    private List<Citizenship> citizenships;

    /**
     * The classification of the national identification number provided.
     * [Required] for the US ISV Seller variants only; not part of the other v3.0 schemas, leave unset
     * for them.
     */
    private NationalIdType nationalIdType;

    /**
     * The representative's national identification number. v3.0 only.
     * [Required] for the US ISV Seller variants; [Optional] for the other v3.0 variants.
     * The format depends on the variant:
     * <ul>
     * <li>US ISV Seller: the number for the {@code nationalIdType} given. ^[a-zA-Z0-9\-]+$, min 5
     * characters, max 16 characters.</li>
     * <li>Other v3.0 variants: a Social Security Number (SSN) or Individual Taxpayer Identification
     * Number (ITIN), US residents only. ^\d{9}$, 9 characters.</li>
     * </ul>
     */
    private String nationalIdNumber;

    /**
     * The representative's personal email address.
     * [Required] for the US ISV Seller variants; [Optional] for the other v3.0 variants.
     * Format: email
     */
    private String emailAddress;

    /**
     * The representative's phone number.
     * [Required] for the US ISV Seller variants; [Optional] for the other v3.0 variants.
     */
    private AccountPhone phone;

    /**
     * The representative's address.
     * [Required]
     */
    private Address address;

}
