package com.checkout.issuing;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.IdResponse;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.PasswordThreeDSEnrollmentRequest;
import com.checkout.issuing.cards.requests.enrollment.ThreeDSUpdateRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeCardRequest;
import com.checkout.issuing.cards.requests.suspend.SuspendCardRequest;
import com.checkout.issuing.cards.responses.CardDetailsResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.cards.responses.credentials.CardCredentialsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentDetailsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSUpdateResponse;
import com.checkout.issuing.controls.requests.create.CardControlRequest;
import com.checkout.issuing.controls.requests.query.CardControlsQuery;
import com.checkout.issuing.controls.requests.update.UpdateCardControlRequest;
import com.checkout.issuing.controls.responses.create.CardControlResponse;
import com.checkout.issuing.controls.responses.query.CardControlsQueryResponse;
import com.checkout.issuing.testing.requests.CardAuthorizationClearingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationIncrementingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationReversalRequest;
import com.checkout.issuing.testing.responses.CardAuthorizationIncrementingResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationReversalResponse;
import com.checkout.payments.VoidResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssuingClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private IssuingClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new IssuingClientImpl(apiClient, configuration);
    }

    @Nested
    @DisplayName("Cardholders")
    class Cardholders {
        @Test
        void shouldCreateCardholder() throws ExecutionException, InterruptedException {
            final CardholderRequest request = createCardholderRequest();
            final CardholderResponse response = createCardholderResponse();

            when(apiClient.postAsync(
                    "issuing/cardholders",
                    authorization,
                    CardholderResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderResponse> future = client.createCardholder(request);

            validateCardholderResponse(response, future.get());
        }

        @Test
        void shouldGetCardholderDetails() throws ExecutionException, InterruptedException {
            final CardholderDetailsResponse response = createCardholderDetailsResponse();

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderDetailsResponse.class))
            .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderDetailsResponse> future = client.getCardholder("cardholder_id");

            validateCardholderDetailsResponse(response, future.get());
        }

        @Test
        void shouldGetCardholderCards() throws ExecutionException, InterruptedException {
            final CardholderCardsResponse response = createCardholderCardsResponse();

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id/cards",
                    authorization,
                    CardholderCardsResponse.class))
                    .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderCardsResponse> future = client.getCardholderCards("cardholder_id");

            validateCardholderCardsResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Cards")
    class Cards {
        @Test
        void shouldCreateCard() throws ExecutionException, InterruptedException {
            final VirtualCardRequest request = createVirtualCardRequest();
            final CardResponse response = createCardResponse();

            when(apiClient.postAsync(
                    "issuing/cards",
                    authorization,
                    CardResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardResponse> future = client.createCard(request);

            validateCardResponse(response, future.get());
        }

        @Test
        void shouldGetCardDetails() throws ExecutionException, InterruptedException {
            final CardDetailsResponse response = createCardDetailsResponse();

            when(apiClient.getAsync(
                    "issuing/cards/card_id",
                    authorization,
                    CardDetailsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardDetailsResponse> future = client.getCardDetails("card_id");

            validateCardDetailsResponse(response, future.get());
        }

        @Test
        void shouldEnrollCardIntoThreeDS() throws ExecutionException, InterruptedException {
            final PasswordThreeDSEnrollmentRequest request = createPasswordThreeDSEnrollmentRequest();
            final ThreeDSEnrollmentResponse response = createThreeDSEnrollmentResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSEnrollmentResponse> future = client.enrollThreeDS("card_id", request);

            validateThreeDSEnrollmentResponse(response, future.get());
        }

        @Test
        void shouldUpdateThreeDSEnrollment() throws ExecutionException, InterruptedException {
            final ThreeDSUpdateRequest request = createThreeDSUpdateRequest();
            final ThreeDSUpdateResponse response = createThreeDSUpdateResponse();

            when(apiClient.patchAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSUpdateResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSUpdateResponse> future = client.updateThreeDS("card_id", request);

            validateThreeDSUpdateResponse(response, future.get());
        }

        @Test
        void shouldGetThreeDSDetails() throws ExecutionException, InterruptedException {
            final ThreeDSEnrollmentDetailsResponse response = createThreeDSEnrollmentDetailsResponse();

            when(apiClient.getAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentDetailsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSEnrollmentDetailsResponse> future = client.getCardThreeDSDetails("card_id");

            validateThreeDSEnrollmentDetailsResponse(response, future.get());
        }

        @Test
        void shouldActivateCard() throws ExecutionException, InterruptedException {
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/activate",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.activateCard("card_id");

            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldGetCardCredentials() throws ExecutionException, InterruptedException {
            final CardCredentialsQuery query = createCardCredentialsQuery();
            final CardCredentialsResponse response = createCardCredentialsResponse();

            when(apiClient.queryAsync(
                    "issuing/cards/card_id/credentials",
                    authorization,
                    query,
                    CardCredentialsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardCredentialsResponse> future = client.getCardCredentials("card_id", query);

            validateCardCredentialsResponse(response, future.get());
        }

        @Test
        void shouldRevokeCard() throws ExecutionException, InterruptedException {
            final RevokeCardRequest request = createRevokeCardRequest();
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/revoke",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.revokeCard("card_id", request);

            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldSuspendCard() throws ExecutionException, InterruptedException {
            final SuspendCardRequest request = createSuspendCardRequest();
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/suspend",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.suspendCard("card_id", request);

            validateVoidResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Controls")
    class Controls {
        @Test
        void shouldCreateControl() throws ExecutionException, InterruptedException {
            final CardControlRequest request = createCardControlRequest();
            final CardControlResponse response = createCardControlResponse();

            when(apiClient.postAsync(
                    "issuing/controls",
                    authorization,
                    CardControlResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.createControl(request);

            validateCardControlResponse(response, future.get());
        }

        @Test
        void shouldGetCardControls() throws ExecutionException, InterruptedException {
            final CardControlsQuery query = createCardControlsQuery();
            final CardControlsQueryResponse response = createCardControlsQueryResponse();

            when(apiClient.queryAsync(
                    "issuing/controls",
                    authorization,
                    query,
                    CardControlsQueryResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlsQueryResponse> future = client.getCardControls(query);

            validateCardControlsQueryResponse(response, future.get());
        }

        @Test
        void shouldGetCardControlDetails() throws ExecutionException, InterruptedException {
            final CardControlResponse response = createCardControlResponse();

            when(apiClient.getAsync(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.getCardControlDetails("control_id");

            validateCardControlResponse(response, future.get());
        }

        @Test
        void shouldUpdateCardControl() throws ExecutionException, InterruptedException {
            final UpdateCardControlRequest request = createUpdateCardControlRequest();
            final CardControlResponse response = createCardControlResponse();

            when(apiClient.putAsync(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class,
                    request
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.updateCardControl("control_id", request);

            validateCardControlResponse(response, future.get());
        }

        @Test
        void shouldRemoveCardControlDetails() throws ExecutionException, InterruptedException {
            final IdResponse response = createIdResponse();

            when(apiClient.deleteAsync(
                    "issuing/controls/control_id",
                    authorization,
                    IdResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<IdResponse> future = client.removeCardControl("control_id");

            validateIdResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Testing")
    class Testing {
        @Test
        void shouldSimulateAuthorization() throws ExecutionException, InterruptedException {
            final CardAuthorizationRequest request = createCardAuthorizationRequest();
            final CardAuthorizationResponse response = createCardAuthorizationResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations",
                    authorization,
                    CardAuthorizationResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardAuthorizationResponse> future = client.simulateAuthorization(request);

            validateCardAuthorizationResponse(response, future.get());
        }

        @Test
        void shouldSimulateAuthorizationIncrementingAuthorization() throws ExecutionException, InterruptedException {
            final CardAuthorizationIncrementingRequest request = createCardAuthorizationIncrementingRequest();
            final CardAuthorizationIncrementingResponse response = createCardAuthorizationIncrementingResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations/authorization_id/authorizations",
                    authorization,
                    CardAuthorizationIncrementingResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardAuthorizationIncrementingResponse> future =
                    client.simulateIncrementingAuthorization("authorization_id", request);

            validateCardAuthorizationIncrementingResponse(response, future.get());
        }

        @Test
        void shouldSimulateAuthorizationClearing() throws ExecutionException, InterruptedException {
            final CardAuthorizationClearingRequest request = createCardAuthorizationClearingRequest();
            final EmptyResponse response = createEmptyResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations/authorization_id/presentments",
                    authorization,
                    EmptyResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<EmptyResponse> future =
                    client.simulateClearing("authorization_id", request);

            validateEmptyResponse(response, future.get());
        }

        @Test
        void shouldSimulateAuthorizationReversal() throws ExecutionException, InterruptedException {
            final CardAuthorizationReversalRequest request = createCardAuthorizationReversalRequest();
            final CardAuthorizationReversalResponse response = createCardAuthorizationReversalResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations/authorization_id/reversals",
                    authorization,
                    CardAuthorizationReversalResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardAuthorizationReversalResponse> future =
                    client.simulateReversal("authorization_id", request);

            validateCardAuthorizationReversalResponse(response, future.get());
        }
    }

    // Synchronous methods
    @Nested
    @DisplayName("Cardholders Sync")
    class CardholdersSync {
        @Test
        void shouldCreateCardholderSync() {
            final CardholderRequest request = createCardholderRequest();
            final CardholderResponse expectedResponse = createCardholderResponse();

            when(apiClient.post(
                    "issuing/cardholders",
                    authorization,
                    CardholderResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardholderResponse actualResponse = client.createCardholderSync(request);

            validateCardholderResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardholderDetailsSync() {
            final CardholderDetailsResponse expectedResponse = createCardholderDetailsResponse();

            when(apiClient.get(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderDetailsResponse.class))
            .thenReturn(expectedResponse);

            final CardholderDetailsResponse actualResponse = client.getCardholderSync("cardholder_id");

            validateCardholderDetailsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardholderCardsSync() {
            final CardholderCardsResponse expectedResponse = createCardholderCardsResponse();

            when(apiClient.get(
                    "issuing/cardholders/cardholder_id/cards",
                    authorization,
                    CardholderCardsResponse.class))
                    .thenReturn(expectedResponse);

            final CardholderCardsResponse actualResponse = client.getCardholderCardsSync("cardholder_id");

            validateCardholderCardsResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Cards Sync")
    class CardsSync {
        @Test
        void shouldCreateCardSync() {
            final VirtualCardRequest request = createVirtualCardRequest();
            final CardResponse expectedResponse = createCardResponse();

            when(apiClient.post(
                    "issuing/cards",
                    authorization,
                    CardResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardResponse actualResponse = client.createCardSync(request);

            validateCardResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardDetailsSync() {
            final CardDetailsResponse expectedResponse = createCardDetailsResponse();

            when(apiClient.get(
                    "issuing/cards/card_id",
                    authorization,
                    CardDetailsResponse.class
            )).thenReturn(expectedResponse);

            final CardDetailsResponse actualResponse = client.getCardDetailsSync("card_id");

            validateCardDetailsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldEnrollCardIntoThreeDSSync() {
            final PasswordThreeDSEnrollmentRequest request = createPasswordThreeDSEnrollmentRequest();
            final ThreeDSEnrollmentResponse expectedResponse = createThreeDSEnrollmentResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final ThreeDSEnrollmentResponse actualResponse = client.enrollThreeDSSync("card_id", request);

            validateThreeDSEnrollmentResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldUpdateThreeDSEnrollmentSync() {
            final ThreeDSUpdateRequest request = createThreeDSUpdateRequest();
            final ThreeDSUpdateResponse expectedResponse = createThreeDSUpdateResponse();

            when(apiClient.patch(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSUpdateResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final ThreeDSUpdateResponse actualResponse = client.updateThreeDSSync("card_id", request);

            validateThreeDSUpdateResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetThreeDSDetailsSync() {
            final ThreeDSEnrollmentDetailsResponse expectedResponse = createThreeDSEnrollmentDetailsResponse();

            when(apiClient.get(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentDetailsResponse.class
            )).thenReturn(expectedResponse);

            final ThreeDSEnrollmentDetailsResponse actualResponse = client.getCardThreeDSDetailsSync("card_id");

            validateThreeDSEnrollmentDetailsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldActivateCardSync() {
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/activate",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.activateCardSync("card_id");

            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardCredentialsSync() {
            final CardCredentialsQuery query = createCardCredentialsQuery();
            final CardCredentialsResponse expectedResponse = createCardCredentialsResponse();

            when(apiClient.query(
                    "issuing/cards/card_id/credentials",
                    authorization,
                    query,
                    CardCredentialsResponse.class
            )).thenReturn(expectedResponse);

            final CardCredentialsResponse actualResponse = client.getCardCredentialsSync("card_id", query);

            validateCardCredentialsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRevokeCardSync() {
            final RevokeCardRequest request = createRevokeCardRequest();
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/revoke",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.revokeCardSync("card_id", request);

            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSuspendCardSync() {
            final SuspendCardRequest request = createSuspendCardRequest();
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/suspend",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.suspendCardSync("card_id", request);

            validateVoidResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Controls Sync")
    class ControlsSync {
        @Test
        void shouldCreateControlSync() {
            final CardControlRequest request = createCardControlRequest();
            final CardControlResponse expectedResponse = createCardControlResponse();

            when(apiClient.post(
                    "issuing/controls",
                    authorization,
                    CardControlResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardControlResponse actualResponse = client.createControlSync(request);

            validateCardControlResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardControlsSync() {
            final CardControlsQuery query = createCardControlsQuery();
            final CardControlsQueryResponse expectedResponse = createCardControlsQueryResponse();

            when(apiClient.query(
                    "issuing/controls",
                    authorization,
                    query,
                    CardControlsQueryResponse.class
            )).thenReturn(expectedResponse);

            final CardControlsQueryResponse actualResponse = client.getCardControlsSync(query);

            validateCardControlsQueryResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardControlDetailsSync() {
            final CardControlResponse expectedResponse = createCardControlResponse();

            when(apiClient.get(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class
            )).thenReturn(expectedResponse);

            final CardControlResponse actualResponse = client.getCardControlDetailsSync("control_id");

            validateCardControlResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldUpdateCardControlSync() {
            final UpdateCardControlRequest request = createUpdateCardControlRequest();
            final CardControlResponse expectedResponse = createCardControlResponse();

            when(apiClient.put(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class,
                    request
            )).thenReturn(expectedResponse);

            final CardControlResponse actualResponse = client.updateCardControlSync("control_id", request);

            validateCardControlResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRemoveCardControlDetailsSync() {
            final IdResponse expectedResponse = createIdResponse();

            when(apiClient.delete(
                    "issuing/controls/control_id",
                    authorization,
                    IdResponse.class
            )).thenReturn(expectedResponse);

            final IdResponse actualResponse = client.removeCardControlSync("control_id");

            validateIdResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Testing Sync")
    class TestingSync {
        @Test
        void shouldSimulateAuthorizationSync() {
            final CardAuthorizationRequest request = createCardAuthorizationRequest();
            final CardAuthorizationResponse expectedResponse = createCardAuthorizationResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations",
                    authorization,
                    CardAuthorizationResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardAuthorizationResponse actualResponse = client.simulateAuthorizationSync(request);

            validateCardAuthorizationResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSimulateAuthorizationIncrementingAuthorizationSync() {
            final CardAuthorizationIncrementingRequest request = createCardAuthorizationIncrementingRequest();
            final CardAuthorizationIncrementingResponse expectedResponse = createCardAuthorizationIncrementingResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations/authorization_id/authorizations",
                    authorization,
                    CardAuthorizationIncrementingResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardAuthorizationIncrementingResponse actualResponse =
                    client.simulateIncrementingAuthorizationSync("authorization_id", request);

            validateCardAuthorizationIncrementingResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSimulateAuthorizationClearingSync() {
            final CardAuthorizationClearingRequest request = createCardAuthorizationClearingRequest();
            final EmptyResponse expectedResponse = createEmptyResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations/authorization_id/presentments",
                    authorization,
                    EmptyResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final EmptyResponse actualResponse = client.simulateClearingSync("authorization_id", request);

            validateEmptyResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSimulateAuthorizationReversalSync() {
            final CardAuthorizationReversalRequest request = createCardAuthorizationReversalRequest();
            final CardAuthorizationReversalResponse expectedResponse = createCardAuthorizationReversalResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations/authorization_id/reversals",
                    authorization,
                    CardAuthorizationReversalResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardAuthorizationReversalResponse actualResponse =
                    client.simulateReversalSync("authorization_id", request);

            validateCardAuthorizationReversalResponse(expectedResponse, actualResponse);
        }
    }

    // Common methods
    private CardholderRequest createCardholderRequest() {
        return mock(CardholderRequest.class);
    }

    private CardholderResponse createCardholderResponse() {
        return mock(CardholderResponse.class);
    }

    private CardholderDetailsResponse createCardholderDetailsResponse() {
        return mock(CardholderDetailsResponse.class);
    }

    private CardholderCardsResponse createCardholderCardsResponse() {
        return mock(CardholderCardsResponse.class);
    }

    private VirtualCardRequest createVirtualCardRequest() {
        return mock(VirtualCardRequest.class);
    }

    private CardResponse createCardResponse() {
        return mock(CardResponse.class);
    }

    private CardDetailsResponse createCardDetailsResponse() {
        return mock(CardDetailsResponse.class);
    }

    private PasswordThreeDSEnrollmentRequest createPasswordThreeDSEnrollmentRequest() {
        return mock(PasswordThreeDSEnrollmentRequest.class);
    }

    private ThreeDSEnrollmentResponse createThreeDSEnrollmentResponse() {
        return mock(ThreeDSEnrollmentResponse.class);
    }

    private ThreeDSUpdateRequest createThreeDSUpdateRequest() {
        return mock(ThreeDSUpdateRequest.class);
    }

    private ThreeDSUpdateResponse createThreeDSUpdateResponse() {
        return mock(ThreeDSUpdateResponse.class);
    }

    private ThreeDSEnrollmentDetailsResponse createThreeDSEnrollmentDetailsResponse() {
        return mock(ThreeDSEnrollmentDetailsResponse.class);
    }

    private VoidResponse createVoidResponse() {
        return mock(VoidResponse.class);
    }

    private CardCredentialsQuery createCardCredentialsQuery() {
        return mock(CardCredentialsQuery.class);
    }

    private CardCredentialsResponse createCardCredentialsResponse() {
        return mock(CardCredentialsResponse.class);
    }

    private RevokeCardRequest createRevokeCardRequest() {
        return mock(RevokeCardRequest.class);
    }

    private SuspendCardRequest createSuspendCardRequest() {
        return mock(SuspendCardRequest.class);
    }

    private CardControlRequest createCardControlRequest() {
        return mock(CardControlRequest.class);
    }

    private CardControlResponse createCardControlResponse() {
        return mock(CardControlResponse.class);
    }

    private CardControlsQuery createCardControlsQuery() {
        return mock(CardControlsQuery.class);
    }

    private CardControlsQueryResponse createCardControlsQueryResponse() {
        return mock(CardControlsQueryResponse.class);
    }

    private UpdateCardControlRequest createUpdateCardControlRequest() {
        return mock(UpdateCardControlRequest.class);
    }

    private IdResponse createIdResponse() {
        return mock(IdResponse.class);
    }

    private CardAuthorizationRequest createCardAuthorizationRequest() {
        return mock(CardAuthorizationRequest.class);
    }

    private CardAuthorizationResponse createCardAuthorizationResponse() {
        return mock(CardAuthorizationResponse.class);
    }

    private CardAuthorizationIncrementingRequest createCardAuthorizationIncrementingRequest() {
        return mock(CardAuthorizationIncrementingRequest.class);
    }

    private CardAuthorizationIncrementingResponse createCardAuthorizationIncrementingResponse() {
        return mock(CardAuthorizationIncrementingResponse.class);
    }

    private CardAuthorizationClearingRequest createCardAuthorizationClearingRequest() {
        return mock(CardAuthorizationClearingRequest.class);
    }

    private EmptyResponse createEmptyResponse() {
        return mock(EmptyResponse.class);
    }

    private CardAuthorizationReversalRequest createCardAuthorizationReversalRequest() {
        return mock(CardAuthorizationReversalRequest.class);
    }

    private CardAuthorizationReversalResponse createCardAuthorizationReversalResponse() {
        return mock(CardAuthorizationReversalResponse.class);
    }

    private void validateCardholderResponse(CardholderResponse expected, CardholderResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardholderDetailsResponse(CardholderDetailsResponse expected, CardholderDetailsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardholderCardsResponse(CardholderCardsResponse expected, CardholderCardsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardResponse(CardResponse expected, CardResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardDetailsResponse(CardDetailsResponse expected, CardDetailsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateThreeDSEnrollmentResponse(ThreeDSEnrollmentResponse expected, ThreeDSEnrollmentResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateThreeDSUpdateResponse(ThreeDSUpdateResponse expected, ThreeDSUpdateResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateThreeDSEnrollmentDetailsResponse(ThreeDSEnrollmentDetailsResponse expected, ThreeDSEnrollmentDetailsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateVoidResponse(VoidResponse expected, VoidResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardCredentialsResponse(CardCredentialsResponse expected, CardCredentialsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardControlResponse(CardControlResponse expected, CardControlResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardControlsQueryResponse(CardControlsQueryResponse expected, CardControlsQueryResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateIdResponse(IdResponse expected, IdResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardAuthorizationResponse(CardAuthorizationResponse expected, CardAuthorizationResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardAuthorizationIncrementingResponse(CardAuthorizationIncrementingResponse expected, CardAuthorizationIncrementingResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEmptyResponse(EmptyResponse expected, EmptyResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardAuthorizationReversalResponse(CardAuthorizationReversalResponse expected, CardAuthorizationReversalResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
