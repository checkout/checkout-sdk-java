package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationGuest {

    /**
     * The guest's first name.
     * [Optional]
     */
    private String firstName;

    /**
     * The guest's last name.
     * [Optional]
     */
    private String lastName;

    /**
     * The guest's date of birth.
     * [Optional]
     * Format: date (yyyy-MM-dd)
     */
    private LocalDate dateOfBirth;

}
