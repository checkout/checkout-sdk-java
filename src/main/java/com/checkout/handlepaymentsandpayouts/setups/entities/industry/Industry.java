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
     * Airline industry-specific data for flight bookings and related payments
     */
    @SerializedName("airline")
    private AirlineData airlineData;

    /**
     * Accommodation industry-specific data for hotel and cruise bookings and related payments
     */
    @SerializedName("accommodation")
    private List<AccommodationData> accommodationData;
}