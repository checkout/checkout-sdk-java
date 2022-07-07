package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
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
public class RequestPayPalSource extends AbstractRequestSource {

    @SerializedName("invoice_number")
    private String invoiceNumber;

    @SerializedName("recipient_name")
    private String recipientName;

    @SerializedName("logo_url")
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
