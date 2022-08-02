package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FlightLegDetails {

    @SerializedName("flight_number")
    private Long flightNumber;

    @SerializedName("carrier_code")
    private String carrierCode;

    @SerializedName("service_class")
    private String serviceClass;

    @SerializedName("departure_date")
    private String departureDate;

    @SerializedName("departure_time")
    private String departureTime;

    @SerializedName("departure_airport")
    private String departureAirport;

    @SerializedName("arrival_airport")
    private String arrivalAirport;

    @SerializedName("stopover_code")
    private String stopoverCode;

    @SerializedName("fare_basis_code")
    private String fareBasisCode;

}
