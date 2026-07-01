package com.checkout.payments.response;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.payments.PaymentProcessing;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.RiskAssessment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AuthorizationResponse extends Resource {

    private String actionId;

    private Long amount;

    private Currency currency;

    private Boolean approved;

    private PaymentStatus status;

    private String authCode;

    private String responseCode;

    private String responseSummary;

    private Instant expiresOn;

    private PaymentResponseBalances balances;

    private Instant processedOn;

    private String reference;

    private PaymentProcessing processing;

    private String eci;

    private String schemeId;

    private RiskAssessment risk;

}
