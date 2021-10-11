package com.checkout;

import com.checkout.apm.baloto.BalotoClient;
import com.checkout.apm.baloto.BalotoClientImpl;
import com.checkout.apm.fawry.FawryClient;
import com.checkout.apm.fawry.FawryClientImpl;
import com.checkout.apm.giropay.GiropayClient;
import com.checkout.apm.giropay.GiropayClientImpl;
import com.checkout.apm.ideal.IdealClient;
import com.checkout.apm.ideal.IdealClientImpl;
import com.checkout.apm.klarna.KlarnaClient;
import com.checkout.apm.klarna.KlarnaClientImpl;
import com.checkout.apm.oxxo.OxxoClient;
import com.checkout.apm.oxxo.OxxoClientImpl;
import com.checkout.apm.pagofacil.PagoFacilClient;
import com.checkout.apm.pagofacil.PagoFacilImpl;
import com.checkout.apm.rapipago.RapiPagoClient;
import com.checkout.apm.rapipago.RapiPagoClientImpl;
import com.checkout.apm.sepa.SepaClient;
import com.checkout.apm.sepa.SepaClientImpl;

public abstract class AbstractCheckoutApmApi {

    protected final ApiClient apiClient;

    private final BalotoClient balotoClient;
    private final FawryClient fawryClient;
    private final GiropayClient giropayClient;
    private final IdealClient idealClient;
    private final KlarnaClient klarnaClient;
    private final OxxoClient oxxoClient;
    private final PagoFacilClient pagoFacilClient;
    private final RapiPagoClient rapiPagoClient;
    private final SepaClient sepaClient;

    protected AbstractCheckoutApmApi(final ApiClient apiClient,
                                     final CheckoutConfiguration configuration) {
        this.apiClient = apiClient;
        this.balotoClient = new BalotoClientImpl(apiClient, configuration);
        this.fawryClient = new FawryClientImpl(apiClient, configuration);
        this.giropayClient = new GiropayClientImpl(apiClient, configuration);
        this.idealClient = new IdealClientImpl(apiClient, configuration);
        this.klarnaClient = new KlarnaClientImpl(apiClient, configuration);
        this.oxxoClient = new OxxoClientImpl(apiClient, configuration);
        this.pagoFacilClient = new PagoFacilImpl(apiClient, configuration);
        this.rapiPagoClient = new RapiPagoClientImpl(apiClient, configuration);
        this.sepaClient = new SepaClientImpl(apiClient, configuration);
    }

    public BalotoClient balotoClient() {
        return balotoClient;
    }

    public FawryClient fawryClient() {
        return fawryClient;
    }

    /**
     * @deprecated GiropayClient client will be removed in a future version.
     */
    @Deprecated
    public GiropayClient giropayClient() {
        return giropayClient;
    }

    public IdealClient idealClient() {
        return idealClient;
    }

    public OxxoClient oxxoClient() {
        return oxxoClient;
    }

    public PagoFacilClient pagoFacilClient() {
        return pagoFacilClient;
    }

    public RapiPagoClient rapiPagoClient() {
        return rapiPagoClient;
    }

    public KlarnaClient klarnaClient() {
        return klarnaClient;
    }

    public SepaClient sepaClient() {
        return sepaClient;
    }

}
