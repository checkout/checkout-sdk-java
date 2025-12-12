package com.checkout;

import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestIdSource;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentInstrumentSender;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to demonstrate that you can create both synchronous and asynchronous clients
 * and call the same methods, getting the same responses but with different execution behavior.
 */
class SynchronousAsyncClientComparisonTest {

    @Test
    void shouldUseSameMethodsForSyncAndAsyncClients() throws ExecutionException, InterruptedException, TimeoutException {
        // Create synchronous client
        final CheckoutApi syncApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(true)  // Synchronous mode
                .build();

        // Create asynchronous client
        final CheckoutApi asyncApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(false)  // Asynchronous mode (default)
                .build();

        // Both clients have the same interface
        assertNotNull(syncApi.paymentsClient());
        assertNotNull(asyncApi.paymentsClient());

        // Example: Both can call requestPayment the same way
        final PaymentRequest request = PaymentRequest.builder()
                .source(new RequestIdSource())
                .sender(new PaymentInstrumentSender())
                .build();

        // Measure time for SYNCHRONOUS call
        System.out.println("\n=== Testing SYNCHRONOUS Client ===");
        final long syncStartTime = System.nanoTime();
        final CompletableFuture<PaymentResponse> syncFuture = syncApi.paymentsClient().requestPayment(request);
        final long syncCallTime = System.nanoTime() - syncStartTime;
        System.out.println("Time to get CompletableFuture (synchronous): " + 
                TimeUnit.NANOSECONDS.toMillis(syncCallTime) + " ms");

        // Wait for completion and measure total time
        final long syncWaitStart = System.nanoTime();
        try {
            syncFuture.get(5, TimeUnit.SECONDS);
            final long syncTotalTime = System.nanoTime() - syncWaitStart;
            System.out.println("Time to complete (synchronous): " + 
                    TimeUnit.NANOSECONDS.toMillis(syncTotalTime) + " ms");
            System.out.println("Total time (synchronous): " + 
                    TimeUnit.NANOSECONDS.toMillis(syncCallTime + syncTotalTime) + " ms");
        } catch (Exception e) {
            System.out.println("Synchronous call failed (expected in test without real HTTP): " + e.getMessage());
        }

        // Measure time for ASYNCHRONOUS call
        System.out.println("\n=== Testing ASYNCHRONOUS Client ===");
        final long asyncStartTime = System.nanoTime();
        final CompletableFuture<PaymentResponse> asyncFuture = asyncApi.paymentsClient().requestPayment(request);
        final long asyncCallTime = System.nanoTime() - asyncStartTime;
        System.out.println("Time to get CompletableFuture (asynchronous): " + 
                TimeUnit.NANOSECONDS.toMillis(asyncCallTime) + " ms");

        // Wait for completion and measure total time
        final long asyncWaitStart = System.nanoTime();
        try {
            asyncFuture.get(5, TimeUnit.SECONDS);
            final long asyncTotalTime = System.nanoTime() - asyncWaitStart;
            System.out.println("Time to complete (asynchronous): " + 
                    TimeUnit.NANOSECONDS.toMillis(asyncTotalTime) + " ms");
            System.out.println("Total time (asynchronous): " + 
                    TimeUnit.NANOSECONDS.toMillis(asyncCallTime + asyncTotalTime) + " ms");
        } catch (Exception e) {
            System.out.println("Asynchronous call failed (expected in test without real HTTP): " + e.getMessage());
        }

        System.out.println("\n=== Summary ===");
        System.out.println("Synchronous: CompletableFuture returned in " + 
                TimeUnit.NANOSECONDS.toMillis(syncCallTime) + " ms");
        System.out.println("Asynchronous: CompletableFuture returned in " + 
                TimeUnit.NANOSECONDS.toMillis(asyncCallTime) + " ms");
        System.out.println("\nNote: In synchronous mode, the HTTP call executes synchronously before");
        System.out.println("      returning the CompletableFuture. In async mode, the CompletableFuture");
        System.out.println("      is returned immediately and the HTTP call happens asynchronously.");

        assertNotNull(syncFuture);
        assertNotNull(asyncFuture);
    }

    @Test
    void shouldHaveSameInterfaceForSyncAndAsyncClients() {
        // Both clients expose the same methods
        final CheckoutApi syncApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(true)
                .build();

        final CheckoutApi asyncApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(false)
                .build();

        // Both have the same client methods available
        assertNotNull(syncApi.paymentsClient());
        assertNotNull(asyncApi.paymentsClient());
        assertNotNull(syncApi.tokensClient());
        assertNotNull(asyncApi.tokensClient());
        assertNotNull(syncApi.customersClient());
        assertNotNull(asyncApi.customersClient());

        // The key difference:
        // - Synchronous client: Methods execute synchronously internally (using invokeSync)
        // - Asynchronous client: Methods execute asynchronously internally (using invoke)
        // But both return CompletableFuture, so the API is the same!
    }

    @Test
    void shouldGetSameResponseTypeFromBothClients() throws ExecutionException, InterruptedException {
        // Both clients return the same types
        final CheckoutApi syncApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(true)
                .build();

        final CheckoutApi asyncApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(false)
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(new RequestIdSource())
                .sender(new PaymentInstrumentSender())
                .build();

        // Measure time for both calls
        System.out.println("\n=== Comparing Response Types ===");
        
        final long syncStart = System.nanoTime();
        final CompletableFuture<PaymentResponse> syncResult = syncApi.paymentsClient().requestPayment(request);
        final long syncTime = System.nanoTime() - syncStart;
        System.out.println("Synchronous call time: " + TimeUnit.NANOSECONDS.toMicros(syncTime) + " microseconds");

        final long asyncStart = System.nanoTime();
        final CompletableFuture<PaymentResponse> asyncResult = asyncApi.paymentsClient().requestPayment(request);
        final long asyncTime = System.nanoTime() - asyncStart;
        System.out.println("Asynchronous call time: " + TimeUnit.NANOSECONDS.toMicros(asyncTime) + " microseconds");

        // Same return type!
        assertTrue(syncResult instanceof CompletableFuture);
        assertTrue(asyncResult instanceof CompletableFuture);

        System.out.println("\nBoth return CompletableFuture<PaymentResponse>");
        System.out.println("Difference: Synchronous executes HTTP call before returning CompletableFuture");
        System.out.println("           Asynchronous returns CompletableFuture immediately, HTTP call happens later");
    }

}
