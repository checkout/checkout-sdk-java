package com.checkout.marketplace;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.CheckoutException;
import com.checkout.Environment;
import com.checkout.FilesApiConfiguration;
import com.checkout.FilesTransport;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.IdResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketplaceClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private OnboardEntityResponse onboardEntityResponse;

    @Mock
    private Void aVoid;

    @Mock
    private IdResponse idResponse;

    @Mock
    private OnboardEntityDetailsResponse onboardEntityDetailsResponse;

    private MarketplaceClient marketplaceClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        lenient().when(checkoutConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(checkoutConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        this.marketplaceClient = new MarketplaceClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldCreateEntity() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("marketplace/entities"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(onboardEntityResponse));

        final CompletableFuture<OnboardEntityResponse> future = marketplaceClient.createEntity(OnboardEntityRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(onboardEntityResponse, future.get());

    }

    @Test
    void shouldGetEntity() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("marketplace/entities/entity_id"), eq(authorization), eq(OnboardEntityDetailsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(onboardEntityDetailsResponse));

        final CompletableFuture<OnboardEntityDetailsResponse> future = marketplaceClient.getEntity("entity_id");

        assertNotNull(future.get());
        assertEquals(onboardEntityDetailsResponse, future.get());

    }

    @Test
    void shouldUpdateEntity() throws ExecutionException, InterruptedException {

        when(apiClient.putAsync(eq("marketplace/entities/entity_id"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(onboardEntityResponse));

        final CompletableFuture<OnboardEntityResponse> future = marketplaceClient.updateEntity(OnboardEntityRequest.builder().build(), "entity_id");

        assertNotNull(future.get());
        assertEquals(onboardEntityResponse, future.get());

    }

    @Test
    void shouldCreatePaymentInstrument() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("marketplace/entities/entity_id/instruments"), eq(authorization), eq(Void.class), any(MarketplacePaymentInstrument.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(aVoid));

        final CompletableFuture<Void> future = marketplaceClient.createPaymentInstrument(MarketplacePaymentInstrument.builder().build(), "entity_id");

        assertNotNull(future.get());
        assertEquals(aVoid, future.get());

    }

    @Test
    void shouldSubmitFile() throws ExecutionException, InterruptedException {

        lenient().when(checkoutConfiguration.getFilesApiConfiguration()).thenReturn(new FilesApiConfiguration(Environment.SANDBOX));

        when(apiClient.submitFileAsync(any(FilesTransport.class), eq("files"), eq(authorization), any(MarketplaceFileRequest.class), eq(IdResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(idResponse));

        final CompletableFuture<IdResponse> future = marketplaceClient.submitFile(MarketplaceFileRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(idResponse, future.get());

    }

    @Test
    void shouldFailSubmitFile() {
        final CheckoutException checkoutException = assertThrows(
                CheckoutException.class,
                () -> new MarketplaceClientImpl(apiClient, Mockito.mock(CheckoutConfiguration.class)).submitFile(MarketplaceFileRequest.builder().build()),
                "Expected submitFile() to throw, but it didn't"
        );
        assertTrue(checkoutException.getMessage().contains("Files API is not enabled. It must be initialized in the SDK."));
    }

}
