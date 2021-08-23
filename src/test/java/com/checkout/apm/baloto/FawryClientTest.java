package com.checkout.apm.baloto;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.apm.fawry.FawryClient;
import com.checkout.apm.fawry.FawryClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FawryClientTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private Void voidResponse;

    private FawryClient fawryClient;

    @BeforeEach
    public void setUp() {
        this.fawryClient = new FawryClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    public void shouldApprovePayment() throws ExecutionException, InterruptedException {

        when(apiClient.putAsync(eq("/fawry/payments/reference/approval"), any(SecretKeyCredentials.class), eq(Void.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = fawryClient.approve("reference");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

    @Test
    public void shouldCancelPayment() throws ExecutionException, InterruptedException {

        when(apiClient.putAsync(eq("/fawry/payments/reference/cancellation"), any(SecretKeyCredentials.class), eq(Void.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = fawryClient.cancel("reference");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

}
