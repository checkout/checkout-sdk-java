package com.checkout;

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

    protected com.checkout.previous.CheckoutApi previousApi;
    protected CheckoutApi checkoutApi;

    public SandboxTestFixture(final PlatformType platformType) {
        switch (platformType) {
            case PREVIOUS:
                this.previousApi = CheckoutSdk.builder()
                        .previous()
                        .staticKeys()
                        .publicKey(requireNonNull(System.getenv("CHECKOUT_PREVIOUS_PUBLIC_KEY")))
                        .secretKey(requireNonNull(System.getenv("CHECKOUT_PREVIOUS_SECRET_KEY")))
                        .environment(Environment.SANDBOX)
                        .httpClientBuilder(CUSTOM_HTTP_BUILDER)
                        .build();
                break;
            case DEFAULT:
                this.checkoutApi = CheckoutSdk.builder()
                        .staticKeys()
                        .publicKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY")))
                        .secretKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY")))
                        .environment(Environment.SANDBOX)
                        .executor(CUSTOM_EXECUTOR)
                        .build();
                break;
            case DEFAULT_OAUTH:
                this.checkoutApi = CheckoutSdk.builder()
                        .oAuth()
                        .clientCredentials(
                                requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_ID")),
                                requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_SECRET")))
                        .scopes(OAuthScope.FILES, OAuthScope.FLOW, OAuthScope.FX, OAuthScope.GATEWAY,
                                OAuthScope.ACCOUNTS, OAuthScope.SESSIONS_APP, OAuthScope.SESSIONS_BROWSER,
                                OAuthScope.VAULT, OAuthScope.PAYOUTS_BANK_DETAILS, OAuthScope.DISPUTES,
                                OAuthScope.TRANSFERS_CREATE, OAuthScope.TRANSFERS_VIEW, OAuthScope.BALANCES_VIEW,
                                OAuthScope.VAULT_CARD_METADATA)
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
