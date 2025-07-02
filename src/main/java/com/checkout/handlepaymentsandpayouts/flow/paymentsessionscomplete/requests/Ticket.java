package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Ticket {

    /**
     * The ticket's unique identifier.
     */
    private String number;

    /**
     * Date the airline ticket was issued.
     */
    @SerializedName("issue_date")
    private Instant issueDate;

    /**
     * Carrier code of the ticket issuer.
     */
    @SerializedName("issuing_carrier_code")
    private String issuingCarrierCode;

    /**
     * C = Car rental reservation, A = Airline flight reservation, B = Both car rental and airline flight reservations
     * included, N = Unknown.
     */
    @SerializedName("travel_package_indicator")
    private String travelPackageIndicator;

    /**
     * The name of the travel agency.
     */
    @SerializedName("travel_agency_name")
    private String travelAgencyName;

    /**
     * The unique identifier from IATA or ARC for the travel agency that issues the ticket.
     */
    @SerializedName("travel_agency_code")
    private String travelAgencyCode;

}
