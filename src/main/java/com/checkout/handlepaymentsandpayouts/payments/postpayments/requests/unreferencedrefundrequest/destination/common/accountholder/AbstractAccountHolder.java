package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder;

import lombok.Data;
/**
 * Abstract account_holder Class
 * The unreferenced refund destination account holder.
 */
@Data
public abstract class AbstractAccountHolder {

    protected final AccountHolderType type;

    public AbstractAccountHolder(final AccountHolderType type) {
        this.type = type;
    }

}
