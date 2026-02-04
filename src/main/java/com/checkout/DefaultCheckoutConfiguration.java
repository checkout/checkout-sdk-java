package com.checkout;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.util.concurrent.Executor;

import org.apache.http.impl.client.HttpClientBuilder;

class DefaultCheckoutConfiguration implements CheckoutConfiguration {

    private final SdkCredentials sdkCredentials;
    private final HttpClientBuilder httpClientBuilder;
    private final Executor executor;
    private final IEnvironment environment;
    private final EnvironmentSubdomain environmentSubdomain;
    private final TransportConfiguration transportConfiguration;
    private final boolean recordTelemetry;
    private final boolean synchronous;
    private final Resilience4jConfiguration resilience4jConfiguration;

    // Backward compatible constructor (without synchronous and resilience4jConfiguration)
    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor,
                                 final TransportConfiguration transportConfiguration,
                                 final boolean recordTelemetry) {
        this(sdkCredentials, environment, httpClientBuilder, executor, transportConfiguration, recordTelemetry, false, null);
    }

    // Constructor with synchronous but without resilience4jConfiguration
    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor,
                                 final TransportConfiguration transportConfiguration,
                                 final boolean recordTelemetry,
                                 final boolean synchronous) {
        this(sdkCredentials, environment, httpClientBuilder, executor, transportConfiguration, recordTelemetry, synchronous, null);
    }

    // Full constructor with all parameters
    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor,
                                 final TransportConfiguration transportConfiguration,
                                 final boolean recordTelemetry,
                                 final boolean synchronous,
                                 final Resilience4jConfiguration resilience4jConfiguration) {
        validateParams("sdkCredentials", sdkCredentials, "environment", environment, "httpClientBuilder", httpClientBuilder, "executor", executor, "transportConfiguration", transportConfiguration);
        this.sdkCredentials = sdkCredentials;
        this.httpClientBuilder = httpClientBuilder;
        this.executor = executor;
        this.environment = environment;
        this.environmentSubdomain = null;
        this.transportConfiguration = transportConfiguration;
        this.recordTelemetry = recordTelemetry;
        this.synchronous = synchronous;
        this.resilience4jConfiguration = resilience4jConfiguration;
    }

    // Backward compatible constructor with subdomain (without synchronous and resilience4jConfiguration)
    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final EnvironmentSubdomain environmentSubdomain,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor,
                                 final TransportConfiguration transportConfiguration,
                                 final Boolean recordTelemetry) {
        this(sdkCredentials, environment, environmentSubdomain, httpClientBuilder, executor, transportConfiguration, recordTelemetry, false, null);
    }

    // Constructor with subdomain and synchronous but without resilience4jConfiguration
    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final EnvironmentSubdomain environmentSubdomain,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor,
                                 final TransportConfiguration transportConfiguration,
                                 final Boolean recordTelemetry,
                                 final Boolean synchronous) {
        this(sdkCredentials, environment, environmentSubdomain, httpClientBuilder, executor, transportConfiguration, recordTelemetry, synchronous, null);
    }

    // Full constructor with subdomain and all parameters
    DefaultCheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final IEnvironment environment,
                                 final EnvironmentSubdomain environmentSubdomain,
                                 final HttpClientBuilder httpClientBuilder,
                                 final Executor executor,
                                 final TransportConfiguration transportConfiguration,
                                 final Boolean recordTelemetry,
                                 final Boolean synchronous,
                                 final Resilience4jConfiguration resilience4jConfiguration) {
        validateParams("sdkCredentials", sdkCredentials, "environment", environment, "httpClientBuilder", httpClientBuilder, "executor", executor, "transportConfiguration", transportConfiguration);
        this.sdkCredentials = sdkCredentials;
        this.httpClientBuilder = httpClientBuilder;
        this.executor = executor;
        this.environment = environment;
        this.environmentSubdomain = environmentSubdomain;
        this.transportConfiguration = transportConfiguration;
        this.recordTelemetry = recordTelemetry;
        this.synchronous = synchronous != null ? synchronous : false;
        this.resilience4jConfiguration = resilience4jConfiguration;
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
    public EnvironmentSubdomain getEnvironmentSubdomain() {
        return environmentSubdomain;
    }

    @Override
    public TransportConfiguration getTransportConfiguration() {
        return transportConfiguration;
    }

    @Override
    public Boolean isTelemetryEnabled() {
        return this.recordTelemetry;
    }

    @Override
    public Boolean isSynchronous() {
        return this.synchronous;
    }

    @Override
    public Resilience4jConfiguration getResilience4jConfiguration() {
        return this.resilience4jConfiguration;
    }
}
