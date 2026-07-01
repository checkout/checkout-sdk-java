package com.checkout.payments.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentResponseBalances {

    private Long totalAuthorized;

    private Long totalVoided;

    private Long availableToVoid;

    private Long totalCaptured;

    private Long availableToCapture;

    private Long totalRefunded;

    private Long availableToRefund;

}
