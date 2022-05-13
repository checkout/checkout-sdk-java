package com.checkout;

import com.checkout.four.CheckoutApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.opentest4j.AssertionFailedError;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public abstract class SandboxTestFixture {

    private static final Executor CUSTOM_EXECUTOR = Executors.newSingleThreadExecutor();
    private static final HttpClientBuilder CUSTOM_HTTP_BUILDER = HttpClientBuilder.create().setConnectionTimeToLive(3, TimeUnit.SECONDS);
    private static final int TRY_MAX_ATTEMPTS = 10;

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
                                FourOAuthScope.VAULT, FourOAuthScope.PAYOUTS_BANK_DETAILS, FourOAuthScope.DISPUTES,
                                FourOAuthScope.TRANSFERS_CREATE, FourOAuthScope.TRANSFERS_VIEW, FourOAuthScope.BALANCES_VIEW)
                        .environment(Environment.SANDBOX)
                        .executor(CUSTOM_EXECUTOR)
                        .build();
                break;
        }
    }

    protected <T> T blocking(final Supplier<CompletableFuture<T>> supplier) {
        int attempts = 1;
        while (attempts <= TRY_MAX_ATTEMPTS) {
            try {
                return supplier.get().get();
            } catch (final Throwable e) {
                log.warn("Request failed with error '{}' - retry {}/{}", e.getMessage(), attempts, TRY_MAX_ATTEMPTS);
            }
            attempts++;
            nap();
        }
        throw new AssertionFailedError("Max attempts reached!");
    }

    protected <T extends HttpMetadata> T blocking(final Supplier<CompletableFuture<T>> supplier, final Matcher<T> matcher) {
        int attempts = 1;
        while (attempts <= TRY_MAX_ATTEMPTS) {
            try {
                final CompletableFuture<T> t = supplier.get();
                if (!matcher.matches(t.get())) {
                    throw new AssertionFailedError(matcher.toString());
                }
                final T value = t.get();
                assertNotNull(value.getHttpStatusCode());
                assertFalse(value.getResponseHeaders().isEmpty());
                return value;
            } catch (final Throwable e) {
                log.warn("Request/Matcher failed with error '{}' - retry {}/{}", e.getMessage(), attempts, TRY_MAX_ATTEMPTS);
            }
            attempts++;
            nap();
        }
        throw new AssertionFailedError("Max attempts reached!");
    }

    private void nap() {
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (final InterruptedException ignore) {
            fail();
        }
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

    // Hamcrest hasSize() doesn't seem to provide the type inference with generics needed
    protected static class ListHasSize<T, R> extends BaseMatcher<ItemsResponse<R>> {

        private final int count;

        public ListHasSize(final int count) {
            this.count = count;
        }

        @Override
        public boolean matches(final Object actual) {
            return ((ItemsResponse<R>) actual).getItems().size() == count;
        }

        @Override
        public void describeTo(final Description description) {
            throw new UnsupportedOperationException();
        }

    }
}
