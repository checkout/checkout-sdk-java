package com.checkout.sessions;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.common.ThreeDSFlowType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetSessionResponse extends Resource {

    /**
     * Session unique identifier.
     * [Required]
     * ^(sid)_(\w{26})$
     * min 30 characters
     * max 30 characters
     */
    private String id;

    /**
     * A base64 encoded value prefixed with {@code sek_} that gives access to client-side operations
     * for a single authentication within the Sessions API.
     * [Optional]
     * ^(sek)_(.{44})$
     * min 48 characters
     * max 48 characters
     */
    private String sessionSecret;

    /**
     * The transaction identifier that needs to be provided when communicating directly with the
     * Access Control Server (ACS).
     * [Required]
     * min 36 characters
     * max 36 characters
     */
    private String transactionId;

    /**
     * Indicates the scheme this authentication is carried out against.
     * [Required]
     */
    private SessionScheme scheme;

    /**
     * The amount in the minor currency.
     * [Required]
     * min 0
     * max 9007199254740991
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
     * Indicates whether this session has been completed.
     * [Optional]
     */
    private Boolean completed;

    /**
     * Indicates whether this session involved a challenge. This will only be set after
     * communication with the scheme is finished.
     * [Optional]
     */
    private Boolean challenged;

    /**
     * Indicates the type of payment this session is for. Please note the spelling of
     * {@code installment} consists of two {@code l}s.
     * [Required]
     */
    private AuthenticationType authenticationType;

    /**
     * Indicates the category of the authentication request.
     * [Required]
     */
    private Category authenticationCategory;

    /**
     * Public certificates specific to a Directory Server (DS) for encrypting device data and
     * verifying ACS signed content. Required when the channel is {@code app}.
     * [Optional]
     */
    private DsPublicKeys certificates;

    /**
     * Indicates the status of the session.
     * [Required]
     */
    private SessionStatus status;

    /**
     * When the session is unavailable, this points to the reason why. For example,
     * {@code ares_error} indicates there was an issue in the authentication response returned by
     * the Directory Server, and {@code ares_status} indicates the status was set to the status in
     * that authentication response.
     * [Optional]
     */
    private StatusReason statusReason;

    /**
     * Whether the authentication was successful. This will only be set if the session is in a final
     * state.
     * [Optional]
     */
    private Boolean approved;

    /**
     * The protocol version number of the specification used by the API for authentication.
     * [Required]
     * max 50 characters
     */
    private String protocolVersion;

    /**
     * Additional information about the cardholder's account.
     * [Optional]
     */
    @SerializedName("account_info")
    private CardholderAccountInfo cardholderAccountInfo;

    /**
     * Additional information about the cardholder's purchase.
     * [Optional]
     */
    private MerchantRiskInfo merchantRiskInfo;

    /**
     * A reference you can later use to identify this payment, such as an order number.
     * [Optional]
     * max 100 characters
     */
    private String reference;

    /**
     * Identifies the type of transaction being authenticated.
     * [Optional]
     * Default: {@link TransactionType#GOODS_SERVICE}
     * max 50 characters
     */
    private TransactionType transactionType;

    /**
     * Specifies which action to take in order to complete the session.
     * The {@code redirect_cardholder} action is only applicable for hosted sessions.
     * [Optional]
     */
    private List<NextAction> nextActions;

    /**
     * The directory server (DS) information. Can be empty if the session is pending or
     * communication with the DS failed.
     * [Optional]
     */
    private Ds ds;

    /**
     * The access control server (ACS) information. Can be empty if the session is still pending or
     * if communication with the ACS failed. This will be available when the channel data and issuer
     * fingerprint result have been provided.
     * [Optional]
     */
    private Acs acs;

    /**
     * Only available as a result of a 3DS2 authentication. The response from the DS or ACS which
     * indicates whether a transaction qualifies as an authenticated transaction or account
     * verification. Only available if communication with the scheme was successful and the session
     * is in a final state.
     * [Optional]
     */
    private ResponseCode responseCode;

    /**
     * Only available as a result of a 3DS2 authentication. The response from the DS or ACS which
     * provides information on why the {@link #responseCode} field has the specified value. Only
     * available when {@link #responseCode} is not {@code Y}.
     * [Optional]
     */
    private String responseStatusReason;

    /**
     * The 3DS1 payer authentication request message.
     * <p>
     * Note: this field is not present in the {@code GetSessionResponse} schema of the Checkout.com
     * API Reference. It is retained for backwards compatibility pending confirmation from the API
     * owners.
     * [Optional]
     */
    private String pareq;

    /**
     * Payment system-specific value provided as part of the ACS registration for each supported DS.
     * This field is only included in responses when authenticating with a valid OAuth token, and not
     * when authenticating with {@link #sessionSecret}.
     * [Optional]
     * min 28 characters
     * max 28 characters
     */
    private String cryptogram;

    /**
     * Electronic Commerce Indicator. This field is only included in responses when authenticating
     * with a valid OAuth token, and not when authenticating with {@link #sessionSecret}.
     * [Optional]
     * min 2 characters
     * max 2 characters
     */
    private String eci;

    /**
     * The xid value to use for authorization.
     * [Optional]
     */
    private String xid;

    /**
     * May provide cardholder information from the DS to be presented to the cardholder.
     * [Optional]
     */
    private String cardholderInfo;

    /**
     * Details related to the session source. This property should always be in the response, unless
     * a {@code card} source was used and communication with Checkout.com's Vault was not possible.
     * [Optional]
     */
    private CardInfo card;

    /**
     * Details of a recurring authentication.
     * [Optional]
     */
    private Recurring recurring;

    /**
     * Details of an installment authentication.
     * [Optional]
     */
    private Installment installment;

    /**
     * Details of a previous transaction.
     * [Optional]
     */
    private InitialTransaction initialTransaction;

    /**
     * Indicates the cardholder's IP address. Only available when the scheme selected is Cartes
     * Bancaires.
     * [Optional]
     */
    private String customerIp;

    /**
     * Authentication date and time.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant authenticationDate;

    /**
     * Details related to the exemption present in the 3DS flow.
     * [Optional]
     */
    private ThreeDSExemption exemption;

    /**
     * Indicates whether the 3D Secure 2 authentication was challenged or frictionless.
     * [Optional]
     */
    private ThreeDSFlowType flowType;

    /**
     * Indicates the preference for whether or not a 3DS challenge should be performed. The
     * customer's bank has the final say on whether or not the customer receives the challenge.
     * <p>
     * Note: the API Reference specifies only the four base values for this response field, but the
     * request accepts nine. This is typed as {@link SessionChallengeIndicator} so that an exemption
     * value echoed back by the API still deserializes; see
     * {@link SessionRequest#getChallengeIndicator()}.
     * [Required]
     * Default: {@link SessionChallengeIndicator#NO_PREFERENCE}
     */
    private SessionChallengeIndicator challengeIndicator;

    /**
     * The information about the optimization options selected.
     * [Optional]
     */
    private Optimization optimization;

    /**
     * Indicates scheme-specific information.
     * [Optional]
     */
    private SchemeInfo schemeInfo;

}
