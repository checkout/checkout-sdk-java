package com.checkout;

import com.checkout.apm.baloto.BalotoClient;
import com.checkout.apm.fawry.FawryClient;
import com.checkout.apm.ideal.IdealClient;
import com.checkout.apm.klarna.KlarnaClient;
import com.checkout.apm.oxxo.OxxoClient;
import com.checkout.apm.pagofacil.PagoFacilClient;
import com.checkout.apm.rapipago.RapiPagoClient;
import com.checkout.apm.sepa.SepaClient;

public interface CheckoutApmApi {

    BalotoClient balotoClient();

    FawryClient fawryClient();

    IdealClient idealClient();

    KlarnaClient klarnaClient();

    OxxoClient oxxoClient();

    PagoFacilClient pagoFacilClient();

    RapiPagoClient rapiPagoClient();

    SepaClient sepaClient();

}