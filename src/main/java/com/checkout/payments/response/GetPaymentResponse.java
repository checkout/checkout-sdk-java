package com.checkout.payments.response;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentActionSummary;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.PaymentType;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSData;
import com.checkout.payments.response.destination.PaymentResponseDestination;
import com.checkout.payments.response.source.ResponseSource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetPaymentResponse extends Resource {

    private String id;

    @SerializedName("requested_on")
    private Instant requestedOn;

    private ResponseSource source;

    private PaymentResponseDestination destination;

    private Long amount;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    private String reference;

    private String description;

    private Boolean approved;

    private PaymentStatus status;

    @SerializedName("3ds")
    private ThreeDSData threeDS;

    private RiskAssessment risk;

    private CustomerResponse customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

    @SerializedName("payment_ip")
    private String paymentIp;

    private PaymentRecipient recipient;

    private Map<String, Object> metadata = new HashMap<>();

    private String eci;

    @SerializedName("scheme_id")
    private String schemeId;

    private List<PaymentActionSummary> actions = new ArrayList<>();

}