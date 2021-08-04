package com.checkout.payments.beta.sender;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestInstrumentSender extends RequestSender {

    @Builder
    private RequestInstrumentSender() {
        super(RequestSenderType.INSTRUMENT);
    }

}
