package com.checkout.sessions;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateSessionAcceptedResponse extends Resource {

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
     * [Required]
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
     */
    private Currency currency;

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
     * The status of the session.
     * [Required]
     */
    private SessionStatus status;

    /**
     * When the session is unavailable, this points to the reason why.
     * <p>
     * Note: this field is not present in the {@code CreateSessionAcceptedResponse} schema of the
     * Checkout.com API Reference, where it appears only on {@code GET /sessions/{id}}. It is
     * retained for backwards compatibility pending confirmation from the API owners.
     * [Optional]
     */
    private StatusReason statusReason;

    /**
     * Specifies which action to take in order to complete the session.
     * The {@code redirect_cardholder} action is only applicable for hosted sessions.
     * [Required]
     */
    private List<NextAction> nextActions;

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
    private CardholderAccountInfo accountInfo;

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
     * Authentication date and time.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant authenticationDate;

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

}
