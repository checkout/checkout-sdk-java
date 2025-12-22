package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import com.checkout.payments.contexts.PaymentContextsAccommodationData;
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
public class Industry {

    /**
     * Airline industry-specific data for flight bookings and related payments
     */
    @SerializedName("airline_data")
    private AirlineData airlineData;

    /**
     * Accommodation industry-specific data for hotel bookings and related payments
     */
    @SerializedName("accommodation_data")
    private List<PaymentContextsAccommodationData> accommodationData;
}