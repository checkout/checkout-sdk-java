package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.carddestination;

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
 * card destination Class
 * The destination of the unreferenced refund.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardDestination extends AbstractDestination {

    /**
     * The card number.
     * [Required]
     * &lt;= 19
     */
    private String number;

    /**
     * The card's expiration month.
     * [Required]
     * [ 1 .. 2 ] characters
     */
    @SerializedName("expiry_month")
    private Integer expiryMonth;

    /**
     * The card's expiration year.
     * [Required]
     * 4 characters
     */
    @SerializedName("expiry_year")
    private Integer expiryYear;

    /**
     * The unreferenced refund destination account holder.
     * [Required]
     */
    @SerializedName("account_holder")
    private AbstractAccountHolder accountHolder;

    /**
     * Initializes a new instance of the CardDestination class.
     */
    @Builder
    private CardDestination(
            final String number,
            final Integer expiryMonth,
            final Integer expiryYear,
            final AbstractAccountHolder accountHolder
    ) {
        super(DestinationType.CARD);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.accountHolder = accountHolder;
    }

    public CardDestination() {
        super(DestinationType.CARD);
    }

}
