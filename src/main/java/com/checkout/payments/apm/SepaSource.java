package com.checkout.payments.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.RequestSource;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class SepaSource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.SEPA;

    private final String id;

}
