package com.checkout.payments.response;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.checkout.payments.PaymentProcessing;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.PaymentType;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSEnrollment;
import com.checkout.payments.PaymentPlan;
import com.checkout.payments.response.source.ResponseSource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponse extends Resource implements Serializable {

    private ResponseSource source;

    private String id;

    private PaymentType paymentType;

    private PaymentPlan paymentPlan;

    private String actionId;

    private Long amount;

    private Long amountRequested;

    private Currency currency;

    private boolean approved;

    private PaymentStatus status;

    private String authCode;

    private String responseCode;

    private String responseSummary;

    private Instant expiresOn;

    @SerializedName("3ds")
    private ThreeDSEnrollment threeDSEnrollment;

    private RiskAssessment risk;

    private CustomerResponse customer;

    private PaymentResponseBalances balances;

    private Instant processedOn;

    private String reference;

    private PaymentProcessing processing;

    private String eci;

    private String schemeId;

    private PaymentRetryResponse retry;

    private String paymentIp;

    private PaymentRecipient recipient;

    private ShippingDetails shipping;

    private String description;

    private Map<String, Object> metadata = new HashMap<>();

}