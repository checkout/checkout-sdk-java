package com.checkout.payments.response;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.checkout.payments.PaymentProcessing;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ThreeDSEnrollment;
import com.checkout.payments.response.source.ResponseSource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponse extends Resource {

    @SerializedName("action_id")
    private String actionId;

    private Long amount;

    private Boolean approved;

    @SerializedName("auth_code")
    private String authCode;

    private String id;

    private Currency currency;

    private CustomerResponse customer;

    private ResponseSource source;

    private PaymentStatus status;

    @SerializedName("3ds")
    private ThreeDSEnrollment threeDSEnrollment;

    private String reference;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_summary")
    private String responseSummary;

    private RiskAssessment risk;

    @SerializedName("processed_on")
    private Instant processedOn;

    private PaymentProcessing processing;

    private String eci;

    @SerializedName("scheme_id")
    private String schemeId;

}
