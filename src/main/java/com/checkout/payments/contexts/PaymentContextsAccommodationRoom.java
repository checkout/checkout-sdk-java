package com.checkout.payments.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains information about a room booked by the customer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsAccommodationRoom {

    /**
     * For lodging, contains the nightly rate for one room. For cruise, contains the total cost
     * of the cruise.
     * [Optional]
     */
    private String rate;

    /**
     * For lodging, contains the number of nights charged at the rate provided in the rate field.
     * For cruise, contains the length of the cruise in days.
     * [Optional]
     * <p>
     * The specification declares this as a string, not an integer. Its example is {@code "3"}.
     */
    private String numberOfNightsAtRoomRate;
}
