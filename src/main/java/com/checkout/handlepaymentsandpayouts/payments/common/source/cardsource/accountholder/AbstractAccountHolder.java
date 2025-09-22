package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder;

import lombok.Data;
/**
 * Abstract account_holder Class
 * Information about the account holder of the card
 */
@Data
public abstract class AbstractAccountHolder {

    protected final AccountHolderType type;

    public AbstractAccountHolder(final AccountHolderType type) {
        this.type = type;
    }

}
