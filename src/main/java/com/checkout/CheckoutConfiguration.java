package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;

import static com.checkout.Environment.lookup;
import static com.checkout.PlatformType.CLASSIC;
import static com.checkout.PlatformType.FOUR;
import static com.checkout.common.CheckoutUtils.requiresNonBlank;
import static com.checkout.common.CheckoutUtils.requiresNonNull;

public final class CheckoutConfiguration {

    private PlatformType platformType;
    private String secretKey;
    private String publicKey;

    /**
     * @deprecated Will be removed in a future version
     */
    @Deprecated
    private final String uri;

    private HttpClientBuilder apacheHttpClientBuilder;

    public CheckoutConfiguration(final String publicKey, final String secretKey, final Environment environment) {
        requiresNonNull("environment", environment);
        this.uri = environment.getUri();
        validateAndSetKeys(publicKey, secretKey, FOUR);
    }

    public CheckoutConfiguration(final String publicKey, final String secretKey, final URI uri) {
        requiresNonNull("uri", uri);
        this.uri = uri.toString();
        validateAndSetKeys(publicKey, secretKey, FOUR);
    }

    public CheckoutConfiguration(final String secretKey, final boolean useSandbox) {
        this(null, secretKey, lookup(useSandbox).getUri());
    }

    public CheckoutConfiguration(final String secretKey, final boolean useSandbox, final String publicKey) {
        this(publicKey, secretKey, lookup(useSandbox).getUri());
    }

    public CheckoutConfiguration(final String secretKey, final String uri) {
        this(null, secretKey, uri);
    }

    private CheckoutConfiguration(final String publicKey, final String secretKey, final String uri) {
        requiresNonBlank("uri", uri);
        this.uri = uri;
        validateAndSetKeys(publicKey, secretKey, CLASSIC);
    }

    private void validateAndSetKeys(final String publicKey, final String secretKey, final PlatformType platformType) {
        requiresNonNull("platformType", platformType);
        if (publicKey != null) {
            platformType.validatePublicKey(publicKey);
            this.publicKey = publicKey;
        }
        platformType.validateSecretKey(secretKey);
        this.platformType = platformType;
        this.secretKey = secretKey;
    }

    public PlatformType getCustomerPlatformType() {
        return platformType;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(final String publicKey) {
        CLASSIC.validatePublicKey(publicKey);
        this.publicKey = publicKey;
    }

    public String getUri() {
        return uri;
    }

    public HttpClientBuilder getApacheHttpClientBuilder() {
        return apacheHttpClientBuilder;
    }

    public void setApacheHttpClientBuilder(final HttpClientBuilder apacheHttpClientBuilder) {
        this.apacheHttpClientBuilder = apacheHttpClientBuilder;
    }

}
