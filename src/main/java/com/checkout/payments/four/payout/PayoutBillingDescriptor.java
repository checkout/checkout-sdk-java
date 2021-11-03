package com.checkout.payments.four.payout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class PayoutBillingDescriptor {

    private String purpose;

}