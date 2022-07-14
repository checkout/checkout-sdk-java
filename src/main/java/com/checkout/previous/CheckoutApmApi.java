package com.checkout.previous;

import com.checkout.apm.ideal.IdealClient;
import com.checkout.apm.previous.klarna.KlarnaClient;
import com.checkout.apm.previous.sepa.SepaClient;

public interface CheckoutApmApi {

    IdealClient idealClient();

    KlarnaClient klarnaClient();

    SepaClient sepaClient();

}