package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.sender;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums.SenderType;
import lombok.Data;

@Data
public abstract class AbstractSender {

    /**
     * The type of the sender.
     */
    protected final SenderType type;

    protected AbstractSender(final SenderType type) {
        this.type = type;
    }

}
