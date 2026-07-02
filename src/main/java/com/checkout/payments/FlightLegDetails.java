package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FlightLegDetails {

    private Long flightNumber;

    private String carrierCode;

    private String serviceClass;

    private String departureDate;

    private String departureTime;

    private String departureAirport;

    private String arrivalAirport;

    private String stopoverCode;

    private String fareBasisCode;

}
