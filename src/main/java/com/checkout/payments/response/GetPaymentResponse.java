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
import com.checkout.payments.ProductResponse;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSData;
import com.checkout.payments.request.PaymentInstruction;
import com.checkout.payments.response.destination.PaymentResponseDestination;
import com.checkout.payments.PaymentPlan;
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

    private String authenticationId;

    private String processingChannelId;

    private Instant requestedOn;

    private ResponseSource source;

    private PaymentResponseDestination destination;

    private Sender sender;

    private Long amount;

    private Long amountRequested;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType type;

    private PaymentPlan paymentPlan;

    private String reference;

    private String description;

    private Boolean approved;

    private Instant expiresOn;

    private PaymentStatus status;

    private PaymentResponseBalances balances;

    @SerializedName("3ds")
    private ThreeDSData threeDSData;

    /**
     * Provides information relating to the authentication of the payment.
     * <p>
     * [Optional]
     * </p>
     */
    private PaymentResponseAuthentication authentication;

    private RiskAssessment risk;

    private CustomerResponse customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billing;

    private ShippingDetails shipping;

    private String paymentIp;

    /**
     * @deprecated This property will be removed in the future, and should be used
     * {@link GetPaymentResponse#amountAllocations} instead
     */
    @Deprecated
    private MarketplaceData marketplace;

    private List<AmountAllocations> amountAllocations;

    private PaymentRecipient recipient;

    private ProcessingData processing;

    private List<ProductResponse> items;

    private Map<String, Object> metadata;

    private String eci;

    private String schemeId;

    private List<PaymentActionSummary> actions;

    private PaymentRetryResponse retry;

    private PanProcessedType panTypeProcessed;

    private Boolean ckoNetworkTokenAvailable;

    private String processedOn;

    private PaymentInstruction instruction;

}
