package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Industry-specific payment setup information
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
     * The specification declares {@code industry.airline} as an array. This was previously a
     * single object, so it serialized as the object {@code airline}, a shape the API does not
     * accept, meaning the value never reached the gateway.
     */
    @SerializedName("airline")
    private List<AirlineData> airlineData;

    /**
     * Accommodation industry-specific data for hotel and cruise bookings and related payments.
     * [Optional]
     */
    @SerializedName("accommodation")
    private List<AccommodationData> accommodationData;
}