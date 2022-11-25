package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.Executor;

import static com.checkout.common.CheckoutUtils.validateParams;

class DefaultCheckoutConfiguration implements CheckoutConfiguration {

    private final SdkCredentials sdkCredentials;
    private final HttpClientBuilder httpClientBuilder;
    private final Executor executor;
    private final IEnvironment environment;

    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor) {
        validateParams("sdkCredentials", sdkCredentials, "environment", environment, "httpClientBuilder", httpClientBuilder, "executor", executor);
        this.sdkCredentials = sdkCredentials;
        this.httpClientBuilder = httpClientBuilder;
        this.executor = executor;
        this.environment = environment;
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
}
