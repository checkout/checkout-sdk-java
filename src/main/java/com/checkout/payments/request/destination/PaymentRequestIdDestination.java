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
public final class PaymentRequestIdDestination extends PaymentRequestDestination {

    private String id;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private PaymentRequestIdDestination(final String id,
                                        final AccountHolder accountHolder) {
        super(PaymentDestinationType.ID);
        this.id = id;
        this.accountHolder = accountHolder;
    }

    public PaymentRequestIdDestination() {
        super(PaymentDestinationType.ID);
    }

}
