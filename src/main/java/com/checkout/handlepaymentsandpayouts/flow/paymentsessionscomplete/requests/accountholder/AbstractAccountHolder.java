package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.accountholder;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums.AccountHolderType;
import lombok.Data;

@Data
public abstract class AbstractAccountHolder {

    /**
     * The type of the Account Holder.
     */
    protected final AccountHolderType type;

    protected AbstractAccountHolder(final AccountHolderType type) {
        this.type = type;
    }

}
