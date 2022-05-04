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

    /**
     * @deprecated Won't be supported anymore on further versions
     */
    @Deprecated
    BalotoClient balotoClient();

    /**
     * @deprecated Won't be supported anymore on further versions
     */
    @Deprecated
    FawryClient fawryClient();

    IdealClient idealClient();

    KlarnaClient klarnaClient();

    /**
     * @deprecated Won't be supported anymore on further versions
     */
    @Deprecated
    OxxoClient oxxoClient();

    /**
     * @deprecated Won't be supported anymore on further versions
     */
    @Deprecated
    PagoFacilClient pagoFacilClient();

    /**
     * @deprecated Won't be supported anymore on further versions
     */
    @Deprecated
    RapiPagoClient rapiPagoClient();

    SepaClient sepaClient();

}