package com.checkout.reconciliation;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public final class Action {

    private String type;

    private String id;

    @SerializedName("processed_on")
    private Instant processedOn;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_description")
    private String responseDescription;

    private List<Breakdown> breakdown;

}
