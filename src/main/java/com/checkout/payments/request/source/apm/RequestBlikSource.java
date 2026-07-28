package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Blik source. Use this to process Blik payments in Poland.
 * <p>
 * When {@code source.type} is {@code blik}: {@code currency} must be {@code PLN}, {@code amount}
 * must not exceed 5,000,000 (minor currency unit), and {@code reference} is limited to 35 characters.
 * <p>
 * For customer-initiated payments ({@code merchant_initiated: false}), provide the customer's
 * 6-digit Blik code in {@code processing.partner_code}. For merchant-initiated recurring payments
 * ({@code merchant_initiated: true}), use either {@code source.type: id} with a previous
 * {@code source.id}, or {@code source.type: blik} with {@code partnerAgreementId}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestBlikSource extends AbstractRequestSource {

    /**
     * The Blik PAYID identifying an external partner agreement created with another PSP.
     * Only used when processing merchant-initiated recurring payments
     * ({@code merchant_initiated: true}) without a stored Checkout.com source.
     * [Optional]
     * max 64 characters
     */
    private String partnerAgreementId;

    @Builder
    private RequestBlikSource(final String partnerAgreementId) {
        super(PaymentSourceType.BLIK);
        this.partnerAgreementId = partnerAgreementId;
    }

    public RequestBlikSource() {
        super(PaymentSourceType.BLIK);
    }

}
