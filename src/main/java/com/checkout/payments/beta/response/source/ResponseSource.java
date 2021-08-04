package com.checkout.payments.beta.response.source;

import com.checkout.payments.beta.request.source.PaymentSourceType;
import lombok.Data;

@Data
public abstract class ResponseSource {

    protected PaymentSourceType type;

}