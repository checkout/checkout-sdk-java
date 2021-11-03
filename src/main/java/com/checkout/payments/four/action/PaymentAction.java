package com.checkout.payments.four.action;

import com.checkout.common.Resource;
import com.checkout.payments.Processing;
import com.checkout.payments.four.AuthorizationType;
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

    private PaymentActionType type;

    @SerializedName("processed_on")
    private Instant processedOn;

    private Long amount;

    private boolean approved;

    @SerializedName("auth_code")
    private String authCode;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_summary")
    private String responseSummary;

    @SerializedName("authorization_type")
    private AuthorizationType authorizationType;

    private String reference;

    private Processing processing;

    private Map<String, Object> metadata = new HashMap<>();

}