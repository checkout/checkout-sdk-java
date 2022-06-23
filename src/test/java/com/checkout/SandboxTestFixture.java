package com.checkout;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.opentest4j.AssertionFailedError;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.Assert.fail;

@Slf4j
public class SandboxTestFixture {

    public static final CheckoutConfiguration CONFIGURATION = loadConfiguration();
    private static final int TRY_MAX_ATTEMPTS = 10;
    private CheckoutApi api;

    public SandboxTestFixture() {
        api = new CheckoutApiImpl(new ApiClientImpl(CONFIGURATION), CONFIGURATION);
    }

    private static CheckoutConfiguration loadConfiguration() {
        String secretKey = System.getenv("CHECKOUT_SECRET_KEY");
        String publicKey = System.getenv("CHECKOUT_PUBLIC_KEY");

        if(secretKey == null) {
            throw new IllegalStateException("Please set the checkout.secretKey java variable!");
        }
        if(publicKey == null) {
            throw new IllegalStateException("Please set the checkout.publicKey java variable!");
        }

        CheckoutConfiguration configuration = new CheckoutConfiguration(secretKey, true);
        configuration.setPublicKey(publicKey);
        return configuration;
    }

    public CheckoutApi getApi() {
        return api;
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

    protected <T> T blocking(final Supplier<CompletableFuture<T>> supplier, final Matcher<T> matcher) {
        int attempts = 1;
        while (attempts <= TRY_MAX_ATTEMPTS) {
            try {
                final CompletableFuture<T> t = supplier.get();
                if (!matcher.matches(t.get())) {
                    throw new AssertionFailedError(matcher.toString());
                }
                return t.get();
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
}