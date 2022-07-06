package com.checkout.payments.four.request.destination;

import com.checkout.common.four.AccountHolder;
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
public final class PaymentRequestTokenDestination extends PaymentRequestDestination {

    private String token;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private PaymentRequestTokenDestination(final String token,
                                           final AccountHolder accountHolder) {
        super(PaymentDestinationType.TOKEN);
        this.token = token;
        this.accountHolder = accountHolder;
    }

    public PaymentRequestTokenDestination() {
        super(PaymentDestinationType.TOKEN);
    }

}
