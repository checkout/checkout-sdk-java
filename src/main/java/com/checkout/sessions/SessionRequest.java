package com.checkout.sessions;

import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Currency;
import com.checkout.sessions.channel.ChannelData;
import com.checkout.sessions.completion.CompletionInfo;
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

    private SessionSource source;

    private Long amount;

    private Currency currency;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("marketplace")
    private SessionMarketplaceData marketplace;

    @SerializedName("authentication_type")
    private AuthenticationType authenticationType;

    @SerializedName("authentication_category")
    private Category authenticationCategory;

    @SerializedName("account_info")
    private CardholderAccountInfo cardholderAccountInfo;

    @SerializedName("challenge_indicator")
    private ChallengeIndicator challengeIndicator;

    @SerializedName("billing_descriptor")
    private SessionsBillingDescriptor billingDescriptor;

    private String reference;

    @SerializedName("merchant_risk_info")
    private MerchantRiskInfo merchantRiskInfo;

    @SerializedName("prior_transaction_reference")
    private String priorTransactionReference;

    @SerializedName("transaction_type")
    private TransactionType transactionType;

    @SerializedName("shipping_address")
    private SessionAddress shippingAddress;

    @SerializedName("shipping_address_matches_billing")
    private Boolean shippingAddressMatchesBilling;

    private CompletionInfo completion;

    @SerializedName("channel_data")
    private ChannelData channelData;

    private Recurring recurring;

}

