package com.checkout;

import com.checkout.four.CheckoutApi;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class SandboxTestFixture {

    private static final Executor CUSTOM_EXECUTOR = Executors.newSingleThreadExecutor();
    private static final HttpClientBuilder CUSTOM_HTTP_BUILDER = HttpClientBuilder.create().setConnectionTimeToLive(3, TimeUnit.SECONDS);

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
                        .httpClientBuilder(CUSTOM_HTTP_BUILDER)
                        .build();
                break;
            case FOUR:
                this.fourApi = CheckoutSdk.fourSdk()
                        .staticKeys()
                        .publicKey(requireNonNull(System.getenv("CHECKOUT_FOUR_PUBLIC_KEY")))
                        .secretKey(requireNonNull(System.getenv("CHECKOUT_FOUR_SECRET_KEY")))
                        .environment(Environment.SANDBOX)
                        .executor(CUSTOM_EXECUTOR)
                        .build();
                break;
            case FOUR_OAUTH:
                this.fourApi = CheckoutSdk.fourSdk()
                        .oAuth()
                        .clientCredentials(
                                requireNonNull(System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_ID")),
                                requireNonNull(System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_SECRET")))
                        .scopes(FourOAuthScope.FILES, FourOAuthScope.FLOW, FourOAuthScope.FX, FourOAuthScope.GATEWAY,
                                FourOAuthScope.MARKETPLACE, FourOAuthScope.SESSIONS_APP, FourOAuthScope.SESSIONS_BROWSER,
                                FourOAuthScope.VAULT, FourOAuthScope.PAYOUTS_BANK_DETAILS)
                        .environment(Environment.SANDBOX)
                        .enableFilesApi(Environment.SANDBOX)
                        .executor(CUSTOM_EXECUTOR)
                        .build();
                break;
        }
    }

    protected <T> T blocking(final CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (final Exception e) {
            assertTrue(e.getCause() instanceof CheckoutApiException);
            final CheckoutApiException checkoutException = (CheckoutApiException) e.getCause();
            fail(checkoutException);
        }
        return null;
    }

    protected <T> void assertNotFound(final CompletableFuture<T> future) {
        try {
            future.get();
            fail();
        } catch (final Exception e) {
            assertTrue(e.getCause() instanceof CheckoutApiException);
            assertEquals("The API response status code (" + 404 + ") does not indicate success.", e.getCause().getMessage());
        }
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
