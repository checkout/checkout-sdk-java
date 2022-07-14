package com.checkout.payments.response;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.payments.PaymentProcessing;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.RiskAssessment;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AuthorizationResponse extends Resource {

    @SerializedName("action_id")
    private String actionId;

    private Long amount;

    private Currency currency;

    private Boolean approved;

    private PaymentStatus status;

    @SerializedName("auth_code")
    private String authCode;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_summary")
    private String responseSummary;

    @SerializedName("expires_on")
    private Instant expiresOn;

    private PaymentResponseBalances balances;

    @SerializedName("processed_on")
    private Instant processedOn;

    private String reference;

    private PaymentProcessing processing;

    private String eci;

    @SerializedName("scheme_id")
    private String schemeId;

    private RiskAssessment risk;

}
