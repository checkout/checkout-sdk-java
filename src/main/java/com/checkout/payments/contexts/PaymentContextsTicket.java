package com.checkout.payments.contexts;

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
public final class PaymentContextsTicket {

    private String number;

    @SerializedName("issue_date")
    private Instant issueDate;

    @SerializedName("issuing_carrier_code")
    private String issuingCarrierCode;

    @SerializedName("travel_package_indicator")
    private String travelPackageIndicator;

    @SerializedName("travel_agency_name")
    private String travelAgencyName;

    @SerializedName("travel_agency_code")
    private String travelAgencyCode;
}
