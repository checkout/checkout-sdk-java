package com.checkout.sessions;

import com.checkout.common.Currency;
import com.checkout.common.MarketplaceData;
import com.checkout.sessions.channel.ChannelData;
import com.checkout.sessions.completion.CompletionInfo;
import com.checkout.sessions.source.SessionSource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class SessionRequest {

    private SessionSource source;

    private Long amount;

    private Currency currency;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("marketplace")
    private MarketplaceData marketplace;

    @SerializedName("authentication_type")
    private AuthenticationType authenticationType;

    @SerializedName("authentication_category")
    private Category authenticationCategory;

    @SerializedName("challenge_indicator")
    private ChallengeIndicator challengeIndicator;

    @SerializedName("billing_descriptor")
    private SessionsBillingDescriptor billingDescriptor;

    private String reference;

    @SerializedName("transaction_type")
    private TransactionType transactionType;

    @SerializedName("shipping_address")
    private SessionAddress shippingAddress;

    private CompletionInfo completion;

    @SerializedName("channel_data")
    private ChannelData channelData;

}

