package com.checkout.payments.previous.response.source;

import com.checkout.common.PaymentSourceType;
import lombok.Data;

@Data
public abstract class AbstractResponseSource {

    protected PaymentSourceType type;

    protected String id;

}
