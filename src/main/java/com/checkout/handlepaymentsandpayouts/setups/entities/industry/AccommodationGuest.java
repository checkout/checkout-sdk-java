package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * A guest staying at the accommodation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationGuest {

    /**
     * The first name of the guest.
     * [Optional]
     */
    private String firstName;

    /**
     * The last name of the guest.
     * [Optional]
     */
    private String lastName;

    /**
     * The date of birth of the guest.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate dateOfBirth;
}
