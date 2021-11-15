package com.checkout.payments.four.response.destination;

import com.checkout.payments.PaymentDestinationType;
import lombok.Data;

@Data
public abstract class AbstractPaymentResponseDestination {

    private PaymentDestinationType type;

}