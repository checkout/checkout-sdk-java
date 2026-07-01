package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Ticket {

    private String number;

    private String issueDate;

    private String issuingCarrierCode;

    private String travelAgencyName;

    private String travelAgencyCode;

}
