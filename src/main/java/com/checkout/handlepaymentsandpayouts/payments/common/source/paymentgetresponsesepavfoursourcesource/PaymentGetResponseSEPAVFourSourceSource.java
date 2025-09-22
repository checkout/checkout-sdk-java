package com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponsesepavfoursourcesource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PaymentGetResponseSEPAV4Source source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentGetResponseSEPAVFourSourceSource extends AbstractSource {

    /**
     * The instrument ID
     * [Required]
     */
    private String id;

    /**
     * Initializes a new instance of the PaymentGetResponseSEPAVFourSourceSource class.
     */
    @Builder
    private PaymentGetResponseSEPAVFourSourceSource(
        final String id
    ) {
        super(SourceType.PAYMENT_GET_RESPONSE_SEPAV4_SOURCE);
        this.id = id;
    }

    public PaymentGetResponseSEPAVFourSourceSource() {
        super(SourceType.PAYMENT_GET_RESPONSE_SEPAV4_SOURCE);
    }

}
