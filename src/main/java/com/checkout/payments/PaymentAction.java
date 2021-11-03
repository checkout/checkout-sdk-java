package com.checkout.payments;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentAction extends Resource {

    private String id;

    private String type;

    @SerializedName("processed_on")
    private Instant processedOn;

    private long amount;

    @SerializedName("auth_code")
    private String authCode;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_summary")
    private String responseSummary;

    private Processing processing;

    private String reference;

    private Map<String, Object> metadata = new HashMap<>();

    private boolean approved;
}