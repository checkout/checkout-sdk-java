package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Contains information about a passenger on the flight.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Passenger {

    /**
     * The passenger's first name.
     * [Optional]
     */
    private String firstName;

    /**
     * The passenger's last name.
     * [Optional]
     */
    private String lastName;

    /**
     * The passenger's date of birth.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate dateOfBirth;

    /**
     * Contains information about the passenger's address.
     * [Optional]
     */
    private PassengerAddress address;
}
