package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.SenderType;
import lombok.Data;

@Data
public abstract class AbstractSender {

    /**
     * The type of sender.
     */
    protected final SenderType type;

    protected AbstractSender(final SenderType type) {
        this.type = type;
    }

}
