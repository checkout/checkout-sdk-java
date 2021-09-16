package com.checkout.payments.hosted;

import com.checkout.common.Product;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.CustomerRequest;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.beta.request.PaymentRecipient;
import com.checkout.payments.beta.request.ProcessingSettings;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public final class HostedPaymentRequest {

    private Long amount;

    @NotEmpty
    private String currency;

    private String reference;

    private String description;

    private CustomerRequest customer;

    private ShippingDetails shippingDetails;

    @NotEmpty
    private BillingInformation billing;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    private List<Product> products;

    private RiskRequest risk;

    @NotEmpty
    @SerializedName("success_url")
    private String successUrl;

    @NotEmpty
    @SerializedName("cancel_url")
    private String cancelUrl;

    @NotEmpty
    @SerializedName("failure_url")
    private String failureUrl;

    private Map<String, Object> metadata;

    private String locale;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    private boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

}
