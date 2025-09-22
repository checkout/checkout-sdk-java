package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.iddestination;

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
 * id destination Class
 * The destination of the unreferenced refund.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdDestination extends AbstractDestination {

    /**
     * The payment source ID. This will be an ID with the prefix src_.
     * [Required]
     * ^(src)_(\w{26})$
     * 30 characters
     */
    private String id;

    /**
     * The unreferenced refund destination account holder.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AbstractAccountHolder accountHolder;

    /**
     * Initializes a new instance of the IdDestination class.
     */
    @Builder
    private IdDestination(
            final String id,
            final AbstractAccountHolder accountHolder
    ) {
        super(DestinationType.ID);
        this.id = id;
        this.accountHolder = accountHolder;
    }

    public IdDestination() {
        super(DestinationType.ID);
    }

}
