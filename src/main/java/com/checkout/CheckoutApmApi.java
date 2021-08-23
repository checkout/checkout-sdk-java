package com.checkout;

import com.checkout.apm.baloto.BalotoClient;
import com.checkout.apm.fawry.FawryClient;
import com.checkout.apm.giropay.GiropayClient;

public interface CheckoutApmApi {

    BalotoClient balotoClient();

    FawryClient fawryClient();

    GiropayClient giropayClient();

}