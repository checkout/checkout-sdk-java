package com.checkout.payments.contexts;

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
    private LocalDate issueDate;

    /**
     * Carrier code of the ticket issuer.
     * [Optional]
     */
    private String issuingCarrierCode;

    /**
     * C = Car rental reservation, A = Airline flight reservation,
     * B = Both car rental and airline flight reservations included, N = Unknown.
     * [Optional]
     */
    private String travelPackageIndicator;

    /**
     * The name of the travel agency.
     * [Optional]
     */
    private String travelAgencyName;

    /**
     * The unique identifier from IATA or ARC for the travel agency that issues the ticket.
     * [Optional]
     */
    private String travelAgencyCode;
}
