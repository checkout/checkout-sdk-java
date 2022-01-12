package com.checkout.payments.links;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Product;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public final class PaymentLinkRequest {

    @NotEmpty
    private Long amount;

    @NotEmpty
    private Currency currency;

    private String reference;

    private String description;

    private Integer expiresIn;

    private CustomerRequest customer;

    private ShippingDetails shipping;

    @NotEmpty
    private BillingInformation billing;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    private List<Product> products;

    private Map<String, Object> metadata;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    private RiskRequest risk;

    private String returnUrl;

    private String locale;

    private boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    @SerializedName("payment_ip")
    public String paymentIp;

    @SerializedName("billing_descriptor")
    public BillingDescriptor billingDescriptor;

}
