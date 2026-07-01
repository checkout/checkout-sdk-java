package com.checkout.payments.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public final class PaymentInstructionResponse {

    private Instant valueDate;

}