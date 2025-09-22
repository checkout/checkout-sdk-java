package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.networktokendestination;

import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.AbstractDestination;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.DestinationType;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.AbstractAccountHolder;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * network_token destination Class
 * The destination of the unreferenced refund.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class NetworkTokenDestination extends AbstractDestination {

    /**
     * The network token's Primary Account Number (PAN).
     * [Required]
     */
    private String token;

    /**
     * The network token's expiration month.
     * [Required]
     * [ 1 .. 2 ] characters &gt;= 1
     */
    @SerializedName("expiry_month")
    private Integer expiryMonth;

    /**
     * The network token's expiration year.
     * [Required]
     * 4 characters
     */
    @SerializedName("expiry_year")
    private Integer expiryYear;

    /**
     * The network token type.
     * [Required]
     */
    @SerializedName("token_type")
    private TokenType tokenType;

    /**
     * The network token's Base64-encoded cryptographic identifier (TAVV).
     * The cryptogram is used by card schemes to validate the token verification result.
     * [Required]
     * &lt;= 50
     */
    private String cryptogram;

    /**
     * The network token's Electronic Commerce Indicator (ECI) security level.
     * [Required]
     * &lt;= 2
     */
    private String eci;

    /**
     * The unreferenced refund destination account holder.
     * [Required]
     */
    @SerializedName("account_holder")
    private AbstractAccountHolder accountHolder;

    /**
     * Initializes a new instance of the NetworkTokenDestination class.
     */
    @Builder
    private NetworkTokenDestination(
            final String token,
            final Integer expiryMonth,
            final Integer expiryYear,
            final TokenType tokenType,
            final String cryptogram,
            final String eci,
            final AbstractAccountHolder accountHolder
    ) {
        super(DestinationType.NETWORK_TOKEN);
        this.token = token;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.tokenType = tokenType;
        this.cryptogram = cryptogram;
        this.eci = eci;
        this.accountHolder = accountHolder;
    }

    public NetworkTokenDestination() {
        super(DestinationType.NETWORK_TOKEN);
    }

}
