package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsTicket {

    /**
     * The ticket's unique identifier.
     * [Optional]
     */
    private String number;

    /**
     * Date the airline ticket was issued.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("issue_date")
    private LocalDate issueDate;

    /**
     * Carrier code of the ticket issuer.
     * [Optional]
     */
    @SerializedName("issuing_carrier_code")
    private String issuingCarrierCode;

    /**
     * C = Car rental reservation, A = Airline flight reservation,
     * B = Both car rental and airline flight reservations included, N = Unknown.
     * [Optional]
     */
    @SerializedName("travel_package_indicator")
    private String travelPackageIndicator;

    /**
     * The name of the travel agency.
     * [Optional]
     */
    @SerializedName("travel_agency_name")
    private String travelAgencyName;

    /**
     * The unique identifier from IATA or ARC for the travel agency that issues the ticket.
     * [Optional]
     */
    @SerializedName("travel_agency_code")
    private String travelAgencyCode;
}
