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
import java.util.Map;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public final class PaymentContextsRequest extends PaymentContexts {

    /**
     * The source of the payment. Discriminated by type: klarna, paypal, stcpay, tabby.
     * [Required]
     */
    private AbstractRequestSource source;

    @Builder
    private PaymentContextsRequest(
            final AbstractRequestSource source,
            final Long amount,
            final Currency currency,
            final PaymentType paymentType,
            final Boolean capture,
            final ShippingDetails shipping,
            final PaymentContextsProcessing processing,
            final String processingChannelId,
            final String reference,
            final String description,
            final String successUrl,
            final String failureUrl,
            final List<PaymentContextsItems> items,
            final String authorizationType,
            final PaymentContextsCustomerRequest customer,
            final Map<String, Object> metadata
    ) {
        super(amount, currency, paymentType, authorizationType, capture, customer, shipping, processing,
                processingChannelId, reference, description, successUrl, failureUrl, items, metadata);
        this.source = source;
    }
}
