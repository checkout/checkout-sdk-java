package com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource.accountholder.AccountHolder;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PaymentGetResponseKlarnaSource source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentGetResponseKlarnaSourceSource extends AbstractSource {

    /**
     * object describes payee details
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    /**
     * Initializes a new instance of the PaymentGetResponseKlarnaSourceSource class.
     */
    @Builder
    private PaymentGetResponseKlarnaSourceSource(
            final AccountHolder accountHolder
    ) {
        super(SourceType.PAYMENT_GET_RESPONSE_KLARNA_SOURCE);
        this.accountHolder = accountHolder;
    }

    public PaymentGetResponseKlarnaSourceSource() {
        super(SourceType.PAYMENT_GET_RESPONSE_KLARNA_SOURCE);
    }

}
