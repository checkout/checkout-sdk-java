package com.checkout.payments.hosted;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostedPaymentInstruction {

    private PaymentPurposeType purpose;
}
