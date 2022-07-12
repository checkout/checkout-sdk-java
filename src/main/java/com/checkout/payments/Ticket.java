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
public final class Ticket {

    private String number;

    @SerializedName("issue_date")
    private String issueDate;

    @SerializedName("issuing_carrier_code")
    private String issuingCarrierCode;

    @SerializedName("travel_agency_name")
    private String travelAgencyName;

    @SerializedName("travel_agency_code")
    private String travelAgencyCode;

}
