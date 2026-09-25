package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Industry-specific information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Industry {

    /**
     * Airline industry-specific data for flight bookings and related payments.
     * [Optional]
     * <p>
     * Maps the specification property {@code airline}, which is an array. This was previously a
     * single object named {@code airlineData}, so it needed an explicit serialized-name override
     * to reach the right key at all, and it serialized as an object where the API expects an
     * array, meaning the value never reached the gateway.
     */
    private List<AirlineData> airline;

    /**
     * Accommodation industry-specific data for hotel and cruise bookings and related payments.
     * [Optional]
     * <p>
     * Maps the specification property {@code accommodation}.
     */
    private List<AccommodationData> accommodation;
}