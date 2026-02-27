package com.checkout.issuing.cards.requests.revocation;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRevocationRequest {

    @SerializedName("revocation_date")
    private String revocationDate;
}