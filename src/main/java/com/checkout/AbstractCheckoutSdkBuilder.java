package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public abstract class AbstractCheckoutSdkBuilder<T extends CheckoutApiClient> {

    protected HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    private IEnvironment environment;
    private EnvironmentSubdomain environmentSubdomain;
    private Executor executor = ForkJoinPool.commonPool();
    private TransportConfiguration transportConfiguration;
    private Boolean recordTelemetry = true;
    private Boolean synchronous = false;
    private Resilience4jConfiguration resilience4jConfiguration;

    public AbstractCheckoutSdkBuilder<T> environment(final IEnvironment environment) {
        this.environment = environment;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> environmentSubdomain(final String subdomain) {
        if (subdomain == null) {
            throw new CheckoutArgumentException("subdomain must be specified");
        }
        this.environmentSubdomain = new EnvironmentSubdomain(this.environment, subdomain);
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

    public AbstractCheckoutSdkBuilder<T> transportConfiguration(final TransportConfiguration transportConfiguration) {
        this.transportConfiguration = transportConfiguration;
        return this;
    }

    protected IEnvironment getEnvironment() {
        return environment;
    }

    protected EnvironmentSubdomain getEnvironmentSubdomain() {
        return environmentSubdomain;
    }

    public AbstractCheckoutSdkBuilder<T> recordTelemetry(final Boolean recordTelemetry) {
        this.recordTelemetry = recordTelemetry;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> synchronous(final Boolean synchronous) {
        this.synchronous = synchronous;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> resilience4jConfiguration(final Resilience4jConfiguration resilience4jConfiguration) {
        this.resilience4jConfiguration = resilience4jConfiguration;
        return this;
    }

    protected abstract SdkCredentials getSdkCredentials();

    protected CheckoutConfiguration getCheckoutConfiguration() {
        if (environment == null) {
            throw new CheckoutArgumentException("environment must be specified");
        }
        final SdkCredentials sdkCredentials = getSdkCredentials();
        if (transportConfiguration == null) {
            transportConfiguration = new DefaultTransportConfiguration();
        }
        return buildCheckoutConfiguration(sdkCredentials);
    }

    private CheckoutConfiguration buildCheckoutConfiguration(final SdkCredentials sdkCredentials) {
        return new DefaultCheckoutConfiguration(sdkCredentials, getEnvironment(), getEnvironmentSubdomain(), httpClientBuilder, executor, transportConfiguration, recordTelemetry, synchronous, resilience4jConfiguration);
    }

    public abstract T build();

}
