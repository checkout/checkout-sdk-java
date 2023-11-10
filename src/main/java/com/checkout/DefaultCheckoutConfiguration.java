package com.checkout;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.util.concurrent.Executor;

import org.apache.http.impl.client.HttpClientBuilder;

class DefaultCheckoutConfiguration implements CheckoutConfiguration {

    private final SdkCredentials sdkCredentials;
    private final HttpClientBuilder httpClientBuilder;
    private final Executor executor;
    private final IEnvironment environment;
    private final TransportConfiguration transportConfiguration;

    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor,
                                 final TransportConfiguration transportConfiguration) {
        validateParams("sdkCredentials", sdkCredentials, "environment", environment, "httpClientBuilder", httpClientBuilder, "executor", executor, "transportConfiguration", transportConfiguration);
        this.sdkCredentials = sdkCredentials;
        this.httpClientBuilder = httpClientBuilder;
        this.executor = executor;
        this.environment = environment;
        this.transportConfiguration = transportConfiguration;
    }

    @Override
    public SdkCredentials getSdkCredentials() {
        return sdkCredentials;
    }

    @Override
    public HttpClientBuilder getHttpClientBuilder() {
        return httpClientBuilder;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public IEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public TransportConfiguration getTransportConfiguration() {
        return transportConfiguration;
    }
}
