package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;

import static com.checkout.common.CheckoutUtils.validateParams;

public final class CheckoutConfiguration {

    private static final String SDK_CREDENTIALS = "sdkCredentials";

    private final String uri;
    private final SdkCredentials sdkCredentials;
    private HttpClientBuilder httpClientBuilder;
    private CheckoutFilesApiConfiguration filesApiConfiguration;

    public CheckoutConfiguration(final SdkCredentials sdkCredentials, final Environment environment) {
        validateParams(SDK_CREDENTIALS, sdkCredentials, "environment", environment);
        this.sdkCredentials = sdkCredentials;
        this.uri = environment.getUri();
    }

    public CheckoutConfiguration(final SdkCredentials sdkCredentials, final URI uri) {
        validateParams(SDK_CREDENTIALS, sdkCredentials, "uri", uri);
        this.sdkCredentials = sdkCredentials;
        this.uri = uri.toString();
    }

    public CheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final Environment environment,
                                 final CheckoutFilesApiConfiguration filesApiConfiguration) {
        validateParams(SDK_CREDENTIALS, sdkCredentials, "environment", environment);
        this.sdkCredentials = sdkCredentials;
        this.uri = environment.getUri();
        this.filesApiConfiguration = filesApiConfiguration;
    }

    public CheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final URI uri,
                                 final CheckoutFilesApiConfiguration filesApiConfiguration) {
        validateParams(SDK_CREDENTIALS, sdkCredentials, "uri", uri);
        this.sdkCredentials = sdkCredentials;
        this.uri = uri.toString();
        this.filesApiConfiguration = filesApiConfiguration;
    }

    public String getUri() {
        return uri;
    }

    public SdkCredentials getSdkCredentials() {
        return sdkCredentials;
    }

    public HttpClientBuilder getApacheHttpClientBuilder() {
        return httpClientBuilder;
    }

    public void setApacheHttpClientBuilder(final HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    public CheckoutFilesApiConfiguration getFilesApiConfiguration() {
        return filesApiConfiguration;
    }

}
