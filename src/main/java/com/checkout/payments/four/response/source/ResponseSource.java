package com.checkout.payments.four.response.source;

import com.checkout.common.PaymentSourceType;
import lombok.Data;

@Data
public abstract class ResponseSource {

    protected PaymentSourceType type;

}