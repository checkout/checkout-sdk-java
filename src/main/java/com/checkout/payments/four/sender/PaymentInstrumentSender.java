package com.checkout.payments.four.sender;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentInstrumentSender extends PaymentSender {

    @Builder
    public PaymentInstrumentSender() {
        super(SenderType.INSTRUMENT);
    }

}
