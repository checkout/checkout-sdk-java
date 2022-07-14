package com.checkout.payments.request.destination;

import com.checkout.common.AccountHolder;
import com.checkout.payments.PaymentDestinationType;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    @SerializedName("account_holder")
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
