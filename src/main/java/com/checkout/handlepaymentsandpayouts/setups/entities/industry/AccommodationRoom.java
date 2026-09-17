package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A room booked by the customer as part of an accommodation booking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationRoom {

    /**
     * The rate of the room.
     * [Optional]
     */
    private Double rate;

    /**
     * The number of nights booked at this room rate.
     * [Optional]
     */
    private Long numberOfNights;

    /**
     * The type of room, for example "deluxe".
     * [Optional]
     */
    private String type;
}
