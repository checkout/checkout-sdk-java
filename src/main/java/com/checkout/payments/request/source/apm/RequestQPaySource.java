package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestQPaySource extends AbstractRequestSource {

    /**
     * The number of items in the order.
     * [Optional]
     */
    private Integer quantity;

    /**
     * A description of the payment.
     * [Optional]
     * max 255
     */
    private String description;

    /**
     * The language to display the payment page in.
     * [Optional]
     */
    private String language;

    /**
     * The customer's national ID.
     * [Optional]
     */
    private String nationalId;

    @Builder
    private RequestQPaySource(final Integer quantity,
                              final String description,
                              final String language,
                              final String nationalId) {
        super(PaymentSourceType.QPAY);
        this.quantity = quantity;
        this.description = description;
        this.language = language;
        this.nationalId = nationalId;
    }

    public RequestQPaySource() {
        super(PaymentSourceType.QPAY);
    }

}
