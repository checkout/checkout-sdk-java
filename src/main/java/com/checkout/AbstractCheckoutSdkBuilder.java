package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.util.concurrent.Executor;

import static java.util.Optional.ofNullable;

public abstract class AbstractCheckoutSdkBuilder<T extends CheckoutApiClient> {

    private Environment environment;
    private Environment filesApiEnvironment;
    private URI uri;
    private HttpClientBuilder httpClientBuilder;
    private Executor executor;

    public AbstractCheckoutSdkBuilder<T> environment(final Environment environment) {
        this.environment = environment;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> uri(final URI uri) {
        this.uri = uri;
        return this;
    }

    public AbstractCheckoutSdkBuilder<T> enableFilesApi(final Environment filesApiEnvironment) {
        this.filesApiEnvironment = filesApiEnvironment;
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

    protected URI getUri() {
        return uri;
    }

    protected abstract SdkCredentials getSdkCredentials();

    protected CheckoutConfiguration getCheckoutConfiguration() {
        if (environment == null && uri == null) {
            throw new CheckoutArgumentException("environment or URI must be specified");
        }
        final SdkCredentials sdkCredentials = getSdkCredentials();
        return buildCheckoutConfiguration(sdkCredentials);
    }

    private CheckoutConfiguration buildCheckoutConfiguration(final SdkCredentials sdkCredentials) {
        final FilesApiConfiguration filesApiConfiguration = ofNullable(filesApiEnvironment).map(FilesApiConfiguration::new).orElse(null);
        if (uri == null) {
            return new DefaultCheckoutConfiguration(sdkCredentials, getEnvironment(), httpClientBuilder, executor, filesApiConfiguration);
        }
        return new DefaultCheckoutConfiguration(sdkCredentials, uri, httpClientBuilder, executor, filesApiConfiguration);
    }

    public abstract T build();

}
