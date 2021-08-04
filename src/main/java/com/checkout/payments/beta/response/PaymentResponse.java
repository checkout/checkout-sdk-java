package com.checkout.payments.beta.response;

import com.checkout.common.beta.Currency;
import com.checkout.common.beta.LinkType;
import com.checkout.common.beta.Resource;
import com.checkout.payments.beta.Processing;
import com.checkout.payments.beta.request.PaymentRecipient;
import com.checkout.payments.beta.request.ShippingDetails;
import com.checkout.payments.beta.response.source.ResponseSource;
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
public class PaymentResponse<S extends ResponseSource> extends Resource implements Serializable {

    private S source;

    private String id;

    @SerializedName("action_id")
    private String actionId;

    private Long amount;

    private Currency currency;

    private boolean approved;

    private PaymentStatus status;

    @SerializedName("auth_code")
    private String authCode;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_summary")
    private String responseSummary;

    @SerializedName("expires_on")
    private Instant expiresOn;

    @SerializedName("3ds")
    private ThreeDSEnrollmentData threeDSEnrollment;

    private RiskAssessment risk;

    private CustomerResponse customer;

    private PaymentResponseBalances balances;

    @SerializedName("processed_on")
    private Instant processedOn;

    private String reference;

    private Processing processing;

    private String eci;

    @SerializedName("scheme_id")
    private String schemeId;

    @SerializedName("payment_ip")
    private final String paymentIp;

    private final PaymentRecipient recipient;

    private ShippingDetails shipping;

    private String description;

    private Map<String, Object> metadata = new HashMap<>();

    public boolean canCapture() {
        return super.hasLink(LinkType.CAPTURE);
    }

}