package com.checkout;

import java.net.URI;

public abstract class AbstractCheckoutSdkBuilder<T extends CheckoutApiClient> {

    private Environment environment;
    private URI uri;
    private CheckoutFilesApiConfiguration filesApiConfiguration = CheckoutFilesApiConfiguration.disabled();

    public AbstractCheckoutSdkBuilder<T> environment(final Environment environment) {
        this.environment = environment;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> uri(final URI uri) {
        this.uri = uri;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> enableFilesApi(final Environment environment) {
        this.filesApiConfiguration = CheckoutFilesApiConfiguration.enabled(environment);
        return this;
    }

    protected Environment getEnvironment() {
        return environment;
    }

    protected URI getUri() {
        return uri;
    }

    protected abstract SdkCredentials getSdkCredentials();

    protected CheckoutConfiguration getCheckoutConfiguration() {
        if (environment == null && uri == null) {
            throw new CheckoutArgumentException("environment or URI must be specified");
        }
        final SdkCredentials sdkCredentials = getSdkCredentials();
        if (uri == null) {
            return new CheckoutConfiguration(sdkCredentials, getEnvironment(), filesApiConfiguration);
        }
        return new CheckoutConfiguration(sdkCredentials, getUri(), filesApiConfiguration);
    }

    public abstract T build();

}
