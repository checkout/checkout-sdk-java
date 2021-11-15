package com.checkout.payments.response.source;

import com.checkout.common.PaymentSourceType;
import lombok.Data;

@Data
public abstract class AbstractResponseSource {

    public PaymentSourceType type;

    public String id;

}
