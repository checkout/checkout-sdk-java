package com.checkout.payments.response.source.contexts;

import com.checkout.common.PaymentSourceType;
import lombok.Data;

@Data
public abstract class AbstractPaymentContextsResponseSource {

    public PaymentSourceType type;
}
