package com.checkout.risk.precapture;

import com.checkout.common.CustomerRequest;
import com.checkout.common.four.Currency;
import com.checkout.risk.Device;
import com.checkout.risk.RiskPayment;
import com.checkout.risk.RiskShippingDetails;
import com.checkout.risk.source.RiskPaymentRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
public final class PreCaptureAssessmentRequest {

    @SerializedName("assessment_id")
    private String assessmentId;

    private Instant date;

    private RiskPaymentRequestSource source;

    private CustomerRequest customer;

    private Long amount;

    private Currency currency;

    private RiskPayment payment;

    private RiskShippingDetails shipping;

    private Device device;

    private Map<String, Object> metadata;

    @SerializedName("authentication_result")
    private AuthenticationResult authenticationResult;

    @SerializedName("authorization_result")
    private AuthorizationResult authorizationResult;

}
