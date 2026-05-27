package com.checkout.handlepaymentsandpayouts.payments.common.source.bliksource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * blik source Class.
 * Use this to process Blik payments in Poland.
 * When {@code source.type} is {@code blik}: currency must be {@code PLN}, amount must not exceed
 * 5,000,000 (minor unit), reference is limited to 35 characters. For customer-initiated payments,
 * provide the 6-digit Blik code in {@code processing.partner_code}. For merchant-initiated recurring
 * payments, use either {@code source.type: id} with a previous {@code source.id}, or
 * {@code source.type: blik} with {@code partner_agreement_id}.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BlikSource extends AbstractSource {

    /**
     * Initializes a new instance of the BlikSource class.
     */
    public BlikSource() {
        super(SourceType.BLIK);
    }

    /**
     * The Checkout.com source identifier for the partner agreement created during a Blik recurring payment.
     * Use this value as {@code source.id} (with {@code source.type: id}) to process subsequent
     * merchant-initiated payments against the same agreement.
     * [Optional] response-only
     */
    private String id;

    /**
     * The Blik PAYID identifying an external partner agreement created with another PSP.
     * Only used when processing merchant-initiated recurring payments
     * ({@code merchant_initiated: true}) without a stored Checkout.com source.
     * [Optional]
     * max 64 characters
     */
    private String partnerAgreementId;
}
