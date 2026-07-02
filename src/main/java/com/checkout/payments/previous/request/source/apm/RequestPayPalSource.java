package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestPayPalSource extends AbstractRequestSource {

    private String invoiceNumber;

    private String recipientName;

    private String logoUrl;

    private Map<String, String> stc;

    @Builder
    private RequestPayPalSource(final String invoiceNumber,
                               final String recipientName,
                               final String logoUrl,
                               final Map<String, String> stc) {
        super(PaymentSourceType.PAYPAL);
        this.invoiceNumber = invoiceNumber;
        this.recipientName = recipientName;
        this.logoUrl = logoUrl;
        this.stc = stc;
    }

    public RequestPayPalSource() {
        super(PaymentSourceType.PAYPAL);
    }

}
