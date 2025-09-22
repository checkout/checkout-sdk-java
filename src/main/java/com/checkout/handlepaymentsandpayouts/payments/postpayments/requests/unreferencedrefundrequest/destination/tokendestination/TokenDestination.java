package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.tokendestination;

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
 * token destination Class
 * The destination of the unreferenced refund.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TokenDestination extends AbstractDestination {

    /**
     * The Checkout.com card token ID. This will be an ID with the prefix tok_.
     * [Required]
     * ^(tok)_(\w{26})$
     * 30 characters
     */
    private String token;

    /**
     * The unreferenced refund destination account holder.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AbstractAccountHolder accountHolder;

    /**
     * Initializes a new instance of the TokenDestination class.
     */
    @Builder
    private TokenDestination(
            final String token,
            final AbstractAccountHolder accountHolder
    ) {
        super(DestinationType.TOKEN);
        this.token = token;
        this.accountHolder = accountHolder;
    }

    public TokenDestination() {
        super(DestinationType.TOKEN);
    }

}
