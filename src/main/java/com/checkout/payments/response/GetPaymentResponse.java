package com.checkout.payments.response;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.MarketplaceData;
import com.checkout.common.Resource;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PanProcessedType;
import com.checkout.payments.PaymentActionSummary;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.PaymentType;
import com.checkout.payments.Product;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSData;
import com.checkout.payments.request.PaymentInstruction;
import com.checkout.payments.response.destination.PaymentResponseDestination;
import com.checkout.payments.PaymentPlanType;
import com.checkout.payments.response.source.ResponseSource;
import com.checkout.payments.sender.Sender;
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

    @SerializedName("payment_plan")
    private PaymentPlanType paymentPlan;

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

    /**
     * @deprecated This property will be removed in the future, and should be used
     * {@link GetPaymentResponse#amountAllocations} instead
     */
    @Deprecated
    private MarketplaceData marketplace;

    @SerializedName("amount_allocations")
    private List<AmountAllocations> amountAllocations;

    private PaymentRecipient recipient;

    private ProcessingData processing;

    private List<Product> items;

    private Map<String, Object> metadata;

    private String eci;

    @SerializedName("scheme_id")
    private String schemeId;

    private List<PaymentActionSummary> actions;

    private PaymentRetryResponse retry;

    @SerializedName("pan_type_processed")
    private PanProcessedType panTypeProcessed;

    @SerializedName("cko_network_token_available")
    private Boolean ckoNetworkTokenAvailable;

    @SerializedName("processed_on")
    private String processedOn;

    private PaymentInstruction instruction;

}
