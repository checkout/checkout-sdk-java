package com.checkout.payments.request.destination;

import com.checkout.common.AccountHolder;
import com.checkout.payments.PaymentDestinationType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentRequestCardDestination extends PaymentRequestDestination {

    private String number;

    private Integer expiryMonth;

    private Integer expiryYear;

    private AccountHolder accountHolder;

    @Builder
    private PaymentRequestCardDestination(final String number,
                                         final Integer expiryMonth,
                                         final Integer expiryYear,
                                         final AccountHolder accountHolder) {
        super(PaymentDestinationType.CARD);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.accountHolder = accountHolder;
    }

    public PaymentRequestCardDestination() {
        super(PaymentDestinationType.CARD);
    }

}
