package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Details about the host of the accommodation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationHost {

    /**
     * The date the host registered.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate registrationDate;

    /**
     * The total number of reservations made by the host.
     * [Optional]
     */
    private Long totalReservationCount;
}
