package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.accountholder;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.AccountHolderType;
import lombok.Data;

@Data
public abstract class AbstractAccountHolder {

    /**
     * The type of account holder.
     */
    protected final AccountHolderType type;

    protected AbstractAccountHolder(final AccountHolderType type) {
        this.type = type;
    }

}
