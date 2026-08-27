package com.checkout.apm.bacs;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BacsClientImplTest {

    private static final String NOTIFICATIONS_PATH = "apms/bacs/notifications";

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private BacsNotificationRequest request;

    @Mock
    private BacsNotificationResponse response;

    private BacsClient bacsClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        lenient().when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.bacsClient = new BacsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldSendNotification() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(NOTIFICATIONS_PATH, authorization, BacsNotificationResponse.class, request, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<BacsNotificationResponse> future = bacsClient.sendNotification(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    // Synchronous methods
    @Test
    void shouldSendNotificationSync() {

        when(apiClient.post(NOTIFICATIONS_PATH, authorization, BacsNotificationResponse.class, request, null))
                .thenReturn(response);

        final BacsNotificationResponse result = bacsClient.sendNotificationSync(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldFailWhenRequestIsNull() {
        assertThrows(CheckoutArgumentException.class, () -> bacsClient.sendNotification(null));
        assertThrows(CheckoutArgumentException.class, () -> bacsClient.sendNotificationSync(null));
    }
}
