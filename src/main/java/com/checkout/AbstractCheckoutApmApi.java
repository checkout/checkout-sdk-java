package com.checkout;

import com.checkout.apm.baloto.BalotoClient;
import com.checkout.apm.baloto.BalotoClientImpl;
import com.checkout.apm.fawry.FawryClient;
import com.checkout.apm.fawry.FawryClientImpl;
import com.checkout.apm.giropay.GiropayClient;
import com.checkout.apm.giropay.GiropayClientImpl;

public abstract class AbstractCheckoutApmApi {

    protected final ApiClient apiClient;

    private final BalotoClient balotoClient;
    private final FawryClient fawryClient;
    private final GiropayClient giropayClient;

    protected AbstractCheckoutApmApi(final ApiClient apiClient,
                                     final CheckoutConfiguration configuration) {
        this.apiClient = apiClient;
        this.balotoClient = new BalotoClientImpl(apiClient, configuration);
        this.fawryClient = new FawryClientImpl(apiClient, configuration);
        this.giropayClient = new GiropayClientImpl(apiClient, configuration);
    }

    public BalotoClient balotoClient() {
        return balotoClient;
    }

    public FawryClient fawryClient() {
        return fawryClient;
    }

    public GiropayClient giropayClient() {
        return giropayClient;
    }

}
