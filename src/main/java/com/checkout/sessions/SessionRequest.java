package com.checkout.sessions;

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

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class SessionRequest {

    /**
     * The source of the authentication.
     * [Required]
     */
    @Builder.Default
    private SessionSource source = new SessionCardSource();

    /**
     * The payment amount in the minor currency unit.
     * For {@code recurring} and {@code installment} payment types, this value is required and must
     * be greater than zero.
     * Omitting this value will set {@link #authenticationCategory} to {@link Category#NON_PAYMENT}.
     * [Optional]
     * min 0
     * max 48 characters
     */
    private Long amount;

    /**
     * The three-letter ISO currency code.
     * [Required]
     * min 3 characters
     * max 3 characters
     */
    private Currency currency;

    /**
     * The processing channel to be used for the session. Required if this was not set in the
     * request for the OAuth token.
     * [Optional]
     * ^(pc)_(\w{26})$
     */
    private String processingChannelId;

    /**
     * Information related to authentication for payfac payments.
     * [Optional]
     */
    private SessionMarketplaceData marketplace;

    /**
     * Indicates the type of payment this session is for. Please note the spelling of
     * {@code installment} consists of two {@code l}s.
     * [Optional]
     * Default: {@link AuthenticationType#REGULAR}
     */
    @Builder.Default
    private AuthenticationType authenticationType = AuthenticationType.REGULAR;

    /**
     * Indicates the category of the authentication request.
     * [Optional]
     * Default: {@link Category#PAYMENT}
     */
    @Builder.Default
    private Category authenticationCategory = Category.PAYMENT;

    /**
     * Additional information about the cardholder's account.
     * [Optional]
     */
    @SerializedName("account_info")
    private CardholderAccountInfo cardholderAccountInfo;

    /**
     * Indicates whether a challenge is requested for this session.
     * The exemption values are accepted only by {@code POST /sessions}; see
     * {@link SessionChallengeIndicator}.
     * [Optional]
     * Default: {@link SessionChallengeIndicator#NO_PREFERENCE}
     * max 50 characters
     */
    @Builder.Default
    private SessionChallengeIndicator challengeIndicator = SessionChallengeIndicator.NO_PREFERENCE;

    /**
     * An optional dynamic billing descriptor.
     * [Optional]
     */
    private SessionsBillingDescriptor billingDescriptor;

    /**
     * A reference you can later use to identify this payment, such as an order number.
     * Do not pass sensitive information in this field, for example card details.
     * [Optional]
     * max 100 characters
     */
    private String reference;

    /**
     * Additional information about the cardholder's purchase.
     * [Optional]
     */
    private MerchantRiskInfo merchantRiskInfo;

    /**
     * Identifies the type of transaction being authenticated.
     * [Optional]
     * Default: {@link TransactionType#GOODS_SERVICE}
     * max 50 characters
     */
    @Builder.Default
    private TransactionType transactionType = TransactionType.GOODS_SERVICE;

    /**
     * The shipping address. Any special characters will be replaced.
     * [Optional]
     */
    private SessionAddress shippingAddress;

    /**
     * Indicates whether the cardholder shipping address and billing address are the same.
     * [Optional]
     */
    private Boolean shippingAddressMatchesBilling;

    /**
     * The redirect information needed for callbacks or redirects after the payment is completed.
     * [Required]
     */
    private CompletionInfo completion;

    /**
     * The information gathered from the environment used to initiate the session.
     * See {@link BrowserSession} for the browser channel.
     * [Optional]
     */
    private ChannelData channelData;

    /**
     * Details of a recurring authentication. This property is needed only for a
     * {@link AuthenticationType#RECURRING} authentication type. Value will be ignored in any other
     * cases.
     * [Optional]
     */
    private Recurring recurring;

    /**
     * Details of an installment authentication. This property is needed only for an
     * {@link AuthenticationType#INSTALLMENT} authentication type. Value will be ignored in any
     * other cases.
     * [Optional]
     */
    private Installment installment;

    /**
     * Optionally opt into request optimization.
     * [Optional]
     */
    private Optimization optimization;

    /**
     * Details of a previous transaction.
     * [Optional]
     */
    private InitialTransaction initialTransaction;

    /**
     * Details of the device from which the authentication originated.
     * [Optional]
     */
    private DeviceInformation deviceInformation;

    /**
     * This object contains the Google SPA properties (non-hosted only).
     * [Optional]
     */
    private GoogleSpa googleSpa;

    /**
     * Indicates the chosen experience(s) for this session.
     * Available experiences are {@link Experience#THREE_DS} and {@link Experience#GOOGLE_SPA}.
     * [Optional]
     */
    private List<Experience> preferredExperiences;

}
