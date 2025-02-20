package com.checkout.payments;

import com.checkout.payments.hosted.PaymentPurposeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentInstruction {

    private PaymentPurposeType purpose;
}
