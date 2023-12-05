package com.checkout.payments.contexts;

import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public final class PaymentContextsRequest extends PaymentContexts {

    private AbstractRequestSource source;

    @Builder
    private PaymentContextsRequest(
            final AbstractRequestSource source,
            final Long amount,
            final Currency currency,
            final PaymentType paymentType,
            final boolean capture,
            final ShippingDetails shipping,
            final PaymentContextsProcessing processing,
            final String processingChannelId,
            final String reference,
            final String description,
            final String successUrl,
            final String failureUrl,
            final List<PaymentContextsItems> items
    ) {
        super();
        this.source = source;
        this.amount = amount;
        this.currency = currency;
        this.paymentType = paymentType;
        this.capture = capture;
        this.shipping = shipping;
        this.processing = processing;
        this.processingChannelId = processingChannelId;
        this.reference = reference;
        this.description = description;
        this.successUrl = successUrl;
        this.failureUrl = failureUrl;
        this.items = items;
    }
}
