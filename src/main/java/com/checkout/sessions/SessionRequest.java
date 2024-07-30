package com.checkout.sessions;

import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Currency;
import com.checkout.sessions.channel.BrowserSession;
import com.checkout.sessions.channel.ChannelData;
import com.checkout.sessions.completion.CompletionInfo;
import com.checkout.sessions.source.SessionCardSource;
import com.checkout.sessions.source.SessionSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class SessionRequest {

    @Builder.Default
    private SessionSource source = new SessionCardSource();

    private Long amount;

    private Currency currency;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("marketplace")
    private SessionMarketplaceData marketplace;

    @Builder.Default
    @SerializedName("authentication_type")
    private AuthenticationType authenticationType = AuthenticationType.REGULAR;

    @Builder.Default
    @SerializedName("authentication_category")
    private Category authenticationCategory = Category.PAYMENT;

    @SerializedName("account_info")
    private CardholderAccountInfo cardholderAccountInfo;

    @Builder.Default
    @SerializedName("challenge_indicator")
    private ChallengeIndicator challengeIndicator = ChallengeIndicator.NO_PREFERENCE;

    @SerializedName("billing_descriptor")
    private SessionsBillingDescriptor billingDescriptor;

    private String reference;

    @SerializedName("merchant_risk_info")
    private MerchantRiskInfo merchantRiskInfo;

    @SerializedName("prior_transaction_reference")
    private String priorTransactionReference;

    @Builder.Default
    @SerializedName("transaction_type")
    private TransactionType transactionType = TransactionType.GOODS_SERVICE;

    @SerializedName("shipping_address")
    private SessionAddress shippingAddress;

    @SerializedName("shipping_address_matches_billing")
    private Boolean shippingAddressMatchesBilling;

    private CompletionInfo completion;

    @SerializedName("channel_data")
    private ChannelData channelData;

    private Recurring recurring;

    private Installment installment;

    private Optimization optimization;

    @SerializedName("initial_transaction")
    private InitialTransaction initialTransaction;

}
