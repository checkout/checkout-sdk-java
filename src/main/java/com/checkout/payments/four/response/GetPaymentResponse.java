package com.checkout.payments.four.response;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.MarketplaceData;
import com.checkout.common.Resource;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentActionSummary;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.PaymentType;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSData;
import com.checkout.payments.four.response.destination.PaymentResponseDestination;
import com.checkout.payments.four.response.source.ResponseSource;
import com.checkout.payments.four.sender.Sender;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
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

    private Sender sender;

    private Long amount;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType type;

    private String reference;

    private String description;

    private Boolean approved;

    @SerializedName("expires_on")
    private Instant expiresOn;

    private PaymentStatus status;

    private PaymentResponseBalances balances;

    @SerializedName("3ds")
    private ThreeDSData threeDSData;

    private RiskAssessment risk;

    private CustomerResponse customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billing;

    private ShippingDetails shipping;

    @SerializedName("payment_ip")
    private String paymentIp;

    private MarketplaceData marketplace;

    private PaymentRecipient recipient;

    private Map<String, Object> metadata;

    private String eci;

    @SerializedName("scheme_id")
    private String schemeId;

    private List<PaymentActionSummary> actions;

}
