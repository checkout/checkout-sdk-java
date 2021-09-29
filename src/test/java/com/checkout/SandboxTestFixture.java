package com.checkout;

import com.checkout.four.CheckoutApi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class SandboxTestFixture {

    protected static final String OAUTH_AUTHORIZE_URL = "https://access.sandbox.checkout.com/connect/token";

    protected static final String SELF = "self";

    protected com.checkout.CheckoutApi defaultApi;
    protected CheckoutApi fourApi;

    public SandboxTestFixture(final PlatformType platformType) {
        switch (platformType) {
            case DEFAULT:
                this.defaultApi = CheckoutSdk.defaultSdk()
                        .staticKeys()
                        .publicKey(requireNonNull(System.getenv("CHECKOUT_PUBLIC_KEY")))
                        .secretKey(requireNonNull(System.getenv("CHECKOUT_SECRET_KEY")))
                        .environment(Environment.SANDBOX)
                        .build();
                break;
            case FOUR:
                this.fourApi = CheckoutSdk.fourSdk()
                        .staticKeys()
                        .publicKey(requireNonNull(System.getenv("CHECKOUT_FOUR_PUBLIC_KEY")))
                        .secretKey(requireNonNull(System.getenv("CHECKOUT_FOUR_SECRET_KEY")))
                        .environment(Environment.SANDBOX)
                        .build();
                break;
            case FOUR_OAUTH:
                try {
                    this.fourApi = CheckoutSdk.fourSdk()
                            .oAuth()
                            .clientCredentials(
                                    new URI(OAUTH_AUTHORIZE_URL),
                                    requireNonNull(System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_ID")),
                                    requireNonNull(System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_SECRET")))
                            .scopes(FourOAuthScope.GATEWAY, FourOAuthScope.FILES, FourOAuthScope.MARKETPLACE)
                            .environment(Environment.SANDBOX)
                            .enableFilesApi(Environment.SANDBOX)
                            .build();
                } catch (final URISyntaxException ignore) {
                    fail();
                }
                break;
        }
    }

    protected <T> T blocking(final CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (final Exception e) {
            fail(e.getMessage());
        }
        return null;
    }

    /**
     * Take a quick nap
     */
    protected void nap() {
        nap(2);
    }

    /**
     * Take a custom nap
     */
    protected void nap(final int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (final InterruptedException ignore) {
            fail();
        }
    }

}