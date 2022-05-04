package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public abstract class AbstractCheckoutSdkBuilder<T extends CheckoutApiClient> {

    protected HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    private Environment environment;
    private Executor executor = ForkJoinPool.commonPool();

    public AbstractCheckoutSdkBuilder<T> environment(final Environment environment) {
        this.environment = environment;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> httpClientBuilder(final HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> executor(final Executor executor) {
        this.executor = executor;
        return this;
    }

    protected Environment getEnvironment() {
        return environment;
    }

    protected abstract SdkCredentials getSdkCredentials();

    protected CheckoutConfiguration getCheckoutConfiguration() {
        if (environment == null) {
            throw new CheckoutArgumentException("environment must be specified");
        }
        final SdkCredentials sdkCredentials = getSdkCredentials();
        return buildCheckoutConfiguration(sdkCredentials);
    }

    private CheckoutConfiguration buildCheckoutConfiguration(final SdkCredentials sdkCredentials) {
        return new DefaultCheckoutConfiguration(sdkCredentials, getEnvironment(), httpClientBuilder, executor);
    }

    public abstract T build();

}
