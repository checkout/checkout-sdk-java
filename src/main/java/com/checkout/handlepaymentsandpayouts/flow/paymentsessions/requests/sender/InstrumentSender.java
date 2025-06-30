package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.SenderType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class InstrumentSender extends AbstractSender {

    /**
     * The sender's reference for the payout
     */
    private String reference;

    @Builder
    private InstrumentSender(final String reference) {
        super(SenderType.INSTRUMENT);
        this.reference = reference;
    }

    public InstrumentSender() {
        super(SenderType.INSTRUMENT);
    }

}
