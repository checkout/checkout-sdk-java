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

    private String processingChannelId;

    private SessionMarketplaceData marketplace;

    @Builder.Default
    private AuthenticationType authenticationType = AuthenticationType.REGULAR;

    @Builder.Default
    private Category authenticationCategory = Category.PAYMENT;

    @SerializedName("account_info")
    private CardholderAccountInfo cardholderAccountInfo;

    @Builder.Default
    private ChallengeIndicator challengeIndicator = ChallengeIndicator.NO_PREFERENCE;

    private SessionsBillingDescriptor billingDescriptor;

    private String reference;

    private MerchantRiskInfo merchantRiskInfo;

    private String priorTransactionReference;

    @Builder.Default
    private TransactionType transactionType = TransactionType.GOODS_SERVICE;

    private SessionAddress shippingAddress;

    private Boolean shippingAddressMatchesBilling;

    private CompletionInfo completion;

    private ChannelData channelData;

    private Recurring recurring;

    private Installment installment;

    private Optimization optimization;

    private InitialTransaction initialTransaction;

    /**
     * Details of the device from which the authentication originated.
     * [Optional]
     */
    private DeviceInformation deviceInformation;

}
