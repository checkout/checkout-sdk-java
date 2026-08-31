package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public abstract class AbstractCheckoutSdkBuilder<T extends CheckoutApiClient> {

    protected HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    private IEnvironment environment;
    private String subdomain;
    private boolean useLegacyDomain;
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
        this.subdomain = subdomain;
        return this;
    }

    /**
     * Opts out of the merchant-specific subdomain, sending every request to the shared
     * hosts instead ({@code api.checkout.com} and {@code access.checkout.com}, or their
     * sandbox equivalents).
     *
     * @deprecated this is an emergency fallback for the rare case where the
     * merchant-specific subdomain cannot be used, and will be removed in a future release.
     * Call {@link #environmentSubdomain(String)} instead.
     * See <a href="https://api-reference.checkout.com/#section/Base-URLs">Base URLs</a>.
     */
    @Deprecated
    public AbstractCheckoutSdkBuilder<T> useLegacyDomain() {
        this.useLegacyDomain = true;
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
        return subdomain != null ? new EnvironmentSubdomain(environment, subdomain) : null;
    }

    /**
     * Whether this builder requires the merchant-specific subdomain to be configured.
     * The Previous (ABC) platform predates merchant-specific subdomains, so it overrides
     * this to {@code false}.
     */
    protected boolean requiresEnvironmentSubdomain() {
        return true;
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
        validateEnvironmentSettings();
        final SdkCredentials sdkCredentials = getSdkCredentials();
        if (transportConfiguration == null) {
            transportConfiguration = new DefaultTransportConfiguration();
        }
        return buildCheckoutConfiguration(sdkCredentials);
    }

    private void validateEnvironmentSettings() {
        if (subdomain != null && useLegacyDomain) {
            throw new CheckoutArgumentException("environmentSubdomain and useLegacyDomain cannot both be set - provide only your merchant-specific subdomain");
        }
        if (subdomain == null && !useLegacyDomain && requiresEnvironmentSubdomain()) {
            throw new CheckoutArgumentException("environmentSubdomain is required - provide your merchant-specific subdomain (typically your client ID excluding the cli_ prefix, see https://api-reference.checkout.com/#section/Base-URLs), or call useLegacyDomain() to opt out only if merchant specific sub domains are causing issues");
        }
    }

    private CheckoutConfiguration buildCheckoutConfiguration(final SdkCredentials sdkCredentials) {
        return new DefaultCheckoutConfiguration(sdkCredentials, getEnvironment(), getEnvironmentSubdomain(), httpClientBuilder, executor, transportConfiguration, recordTelemetry, synchronous, resilience4jConfiguration);
    }

    public abstract T build();

}
