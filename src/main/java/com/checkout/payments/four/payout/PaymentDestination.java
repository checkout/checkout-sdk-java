package com.checkout.payments.four.payout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class PaymentDestination {

    private String type;

    private String id;

    private String number;

    private Integer amount;

}