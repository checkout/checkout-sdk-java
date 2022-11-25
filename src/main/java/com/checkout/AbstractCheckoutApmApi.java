package com.checkout;

import com.checkout.apm.ideal.IdealClient;
import com.checkout.apm.ideal.IdealClientImpl;
import com.checkout.apm.previous.klarna.KlarnaClient;
import com.checkout.apm.previous.klarna.KlarnaClientImpl;
import com.checkout.apm.previous.sepa.SepaClient;
import com.checkout.apm.previous.sepa.SepaClientImpl;

import java.net.URI;

public abstract class AbstractCheckoutApmApi {

    protected final ApiClient apiClient;

    private final IdealClient idealClient;
    private final KlarnaClient klarnaClient;
    private final SepaClient sepaClient;

    protected AbstractCheckoutApmApi(final CheckoutConfiguration configuration) {
        this.apiClient = getBaseApiClient(configuration);
        this.idealClient = new IdealClientImpl(apiClient, configuration);
        this.klarnaClient = new KlarnaClientImpl(apiClient, configuration);
        this.sepaClient = new SepaClientImpl(apiClient, configuration);
    }

    public IdealClient idealClient() {
        return idealClient;
    }

    public KlarnaClient klarnaClient() {
        return klarnaClient;
    }

    public SepaClient sepaClient() {
        return sepaClient;
    }

    private ApiClient getBaseApiClient(final CheckoutConfiguration configuration) {
        return new ApiClientImpl(configuration, new BaseUriStrategy(configuration));
    }

    private static class BaseUriStrategy implements UriStrategy {

        private final CheckoutConfiguration configuration;

        private BaseUriStrategy(final CheckoutConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public URI getUri() {
            return configuration.getEnvironment().getCheckoutApi();
        }
    }

}
