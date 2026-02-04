package com.checkout.apm.previous.sepa;

import com.checkout.ApiClient;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SepaClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private SepaClient sepaClient;

    @BeforeEach
    void setUp() {
        setUpAuthorizationMocks();
        this.sepaClient = new SepaClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldGetMandate() throws ExecutionException, InterruptedException {
        final MandateResponse expectedResponse = mock(MandateResponse.class);

        when(apiClient.getAsync("sepa/mandates/mandate_id", authorization, MandateResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<MandateResponse> future = sepaClient.getMandate("mandate_id");
        final MandateResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCancelMandate() throws ExecutionException, InterruptedException {
        final SepaResource expectedResponse = mock(SepaResource.class);

        when(apiClient.postAsync(eq("sepa/mandates/mandate_id/cancel"), eq(authorization), eq(SepaResource.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<SepaResource> future = sepaClient.cancelMandate("mandate_id");
        final SepaResource actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetMandateViaPPRO() throws ExecutionException, InterruptedException {
        final MandateResponse expectedResponse = mock(MandateResponse.class);

        when(apiClient.getAsync("apms/ppro/sepa/mandates/mandate_id", authorization, MandateResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<MandateResponse> future = sepaClient.getMandateViaPPRO("mandate_id");
        final MandateResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCancelMandateViaPPRO() throws ExecutionException, InterruptedException {
        final SepaResource expectedResponse = mock(SepaResource.class);

        when(apiClient.postAsync(eq("apms/ppro/sepa/mandates/mandate_id/cancel"), eq(authorization), eq(SepaResource.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<SepaResource> future = sepaClient.cancelMandateViaPPRO("mandate_id");
        final SepaResource actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldGetMandateSync() {
        final MandateResponse expectedResponse = mock(MandateResponse.class);

        when(apiClient.get("sepa/mandates/mandate_id", authorization, MandateResponse.class))
                .thenReturn(expectedResponse);

        final MandateResponse actualResponse = sepaClient.getMandateSync("mandate_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCancelMandateSync() {
        final SepaResource expectedResponse = mock(SepaResource.class);

        when(apiClient.post(eq("sepa/mandates/mandate_id/cancel"), eq(authorization), eq(SepaResource.class), isNull(), isNull()))
                .thenReturn(expectedResponse);

        final SepaResource actualResponse = sepaClient.cancelMandateSync("mandate_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetMandateViaPPROSync() {
        final MandateResponse expectedResponse = mock(MandateResponse.class);

        when(apiClient.get("apms/ppro/sepa/mandates/mandate_id", authorization, MandateResponse.class))
                .thenReturn(expectedResponse);

        final MandateResponse actualResponse = sepaClient.getMandateViaPPROSync("mandate_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCancelMandateViaPPROSync() {
        final SepaResource expectedResponse = mock(SepaResource.class);

        when(apiClient.post(eq("apms/ppro/sepa/mandates/mandate_id/cancel"), eq(authorization), eq(SepaResource.class), isNull(), isNull()))
                .thenReturn(expectedResponse);

        final SepaResource actualResponse = sepaClient.cancelMandateViaPPROSync("mandate_id");

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private void setUpAuthorizationMocks() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }

}