package com.checkout.payments.four.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class PayoutBillingDescriptor {

    private String purpose;

}