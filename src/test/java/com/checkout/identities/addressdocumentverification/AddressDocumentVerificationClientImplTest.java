package com.checkout.identities.addressdocumentverification;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationAttemptRequest;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationRequest;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptsResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationReportResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationResponse;
import com.checkout.identities.entities.DeclaredData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressDocumentVerificationClientImplTest {

    private static final String ADV_ID = "adv_tkoi5db4hryu5cei5vwoabr7we";
    private static final String ATTEMPT_ID = "adva_tkoi5db4hryu5cei5vwoabr7we";

    private AddressDocumentVerificationClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new AddressDocumentVerificationClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateAddressDocumentVerification() throws ExecutionException, InterruptedException {
        final AddressDocumentVerificationRequest request = createRequest();
        final AddressDocumentVerificationResponse response = new AddressDocumentVerificationResponse();

        when(apiClient.postAsync("address-document-verifications", authorization,
                AddressDocumentVerificationResponse.class, request, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<AddressDocumentVerificationResponse> future =
                client.createAddressDocumentVerification(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetAddressDocumentVerification() throws ExecutionException, InterruptedException {
        final AddressDocumentVerificationResponse response = new AddressDocumentVerificationResponse();

        when(apiClient.getAsync("address-document-verifications/" + ADV_ID, authorization,
                AddressDocumentVerificationResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        assertEquals(response, client.getAddressDocumentVerification(ADV_ID).get());
    }

    @Test
    void shouldAnonymizeAddressDocumentVerification() throws ExecutionException, InterruptedException {
        final AddressDocumentVerificationResponse response = new AddressDocumentVerificationResponse();

        when(apiClient.postAsync("address-document-verifications/" + ADV_ID + "/anonymize", authorization,
                AddressDocumentVerificationResponse.class, null, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        assertEquals(response, client.anonymizeAddressDocumentVerification(ADV_ID).get());
    }

    @Test
    void shouldCreateAttempt() throws ExecutionException, InterruptedException {
        final AddressDocumentVerificationAttemptRequest request =
                AddressDocumentVerificationAttemptRequest.builder().document("base64-data").build();
        final AddressDocumentVerificationAttemptResponse response = new AddressDocumentVerificationAttemptResponse();

        when(apiClient.postAsync("address-document-verifications/" + ADV_ID + "/attempts", authorization,
                AddressDocumentVerificationAttemptResponse.class, request, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        assertEquals(response, client.createAddressDocumentVerificationAttempt(ADV_ID, request).get());
    }

    @Test
    void shouldGetAttempts() throws ExecutionException, InterruptedException {
        final AddressDocumentVerificationAttemptsResponse response = new AddressDocumentVerificationAttemptsResponse();

        when(apiClient.getAsync("address-document-verifications/" + ADV_ID + "/attempts", authorization,
                AddressDocumentVerificationAttemptsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        assertEquals(response, client.getAddressDocumentVerificationAttempts(ADV_ID).get());
    }

    @Test
    void shouldGetAttempt() throws ExecutionException, InterruptedException {
        final AddressDocumentVerificationAttemptResponse response = new AddressDocumentVerificationAttemptResponse();

        when(apiClient.getAsync("address-document-verifications/" + ADV_ID + "/attempts/" + ATTEMPT_ID, authorization,
                AddressDocumentVerificationAttemptResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        assertEquals(response, client.getAddressDocumentVerificationAttempt(ADV_ID, ATTEMPT_ID).get());
    }

    @Test
    void shouldGetReport() throws ExecutionException, InterruptedException {
        final AddressDocumentVerificationReportResponse response = new AddressDocumentVerificationReportResponse();

        when(apiClient.getAsync("address-document-verifications/" + ADV_ID + "/pdf-report", authorization,
                AddressDocumentVerificationReportResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        assertEquals(response, client.getAddressDocumentVerificationReport(ADV_ID).get());
    }

    private AddressDocumentVerificationRequest createRequest() {
        return AddressDocumentVerificationRequest.builder()
                .applicantId("aplt_tkoi5db4hryu5cei5vwoabr7we")
                .userJourneyId("usj_tkoi5db4hryu5cei5vwoabr7we")
                .declaredData(DeclaredData.builder().name("Hannah Bret").build())
                .build();
    }
}
