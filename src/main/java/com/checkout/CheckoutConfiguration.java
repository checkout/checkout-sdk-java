package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;

import static com.checkout.Environment.lookup;
import static com.checkout.common.CheckoutUtils.validateParams;

public final class CheckoutConfiguration {

    private final String uri;
    private final SdkCredentials sdkCredentials;
    private HttpClientBuilder httpClientBuilder;
    private CheckoutFilesApiConfiguration filesApiConfiguration;

    public CheckoutConfiguration(final SdkCredentials sdkCredentials, final Environment environment) {
        validateParams("sdkCredentials", sdkCredentials, "environment", environment);
        this.sdkCredentials = sdkCredentials;
        this.uri = environment.getUri();
    }

    public CheckoutConfiguration(final SdkCredentials sdkCredentials, final URI uri) {
        validateParams("sdkCredentials", sdkCredentials, "uri", uri);
        this.sdkCredentials = sdkCredentials;
        this.uri = uri.toString();
    }

    public CheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final Environment environment,
                                 final CheckoutFilesApiConfiguration filesApiConfiguration) {
        validateParams("sdkCredentials", sdkCredentials, "environment", environment);
        this.sdkCredentials = sdkCredentials;
        this.uri = environment.getUri();
        this.filesApiConfiguration = filesApiConfiguration;
    }

    public CheckoutConfiguration(final SdkCredentials sdkCredentials,
                                 final URI uri,
                                 final CheckoutFilesApiConfiguration filesApiConfiguration) {
        validateParams("sdkCredentials", sdkCredentials, "uri", uri);
        this.sdkCredentials = sdkCredentials;
        this.uri = uri.toString();
        this.filesApiConfiguration = filesApiConfiguration;
    }

    /**
     * @deprecated Please use {@link CheckoutSdk#defaultSdk()} as the entrypoint to create new SDK instances.
     */
    @Deprecated
    private CheckoutConfiguration(final String publicKey, final String secretKey, final String uri) {
        validateParams("secretKey", secretKey, "uri", uri);
        this.uri = uri;
        this.sdkCredentials = new DefaultStaticKeysSdkCredentials(secretKey, publicKey);
    }

    /**
     * @deprecated Please use {@link CheckoutSdk#defaultSdk()} as the entrypoint to create new SDK instances.
     */
    @Deprecated
    public CheckoutConfiguration(final String secretKey, final boolean useSandbox) {
        this(null, secretKey, lookup(useSandbox).getUri());
    }

    /**
     * @deprecated Please use {@link CheckoutSdk#defaultSdk()} as the entrypoint to create new SDK instances.
     */
    @Deprecated
    public CheckoutConfiguration(final String secretKey, final boolean useSandbox, final String publicKey) {
        this(publicKey, secretKey, lookup(useSandbox).getUri());
    }

    /**
     * @deprecated Please use {@link CheckoutSdk#defaultSdk()} as the entrypoint to create new SDK instances.
     */
    @Deprecated
    public CheckoutConfiguration(final String secretKey, final String uri) {
        this(null, secretKey, uri);
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
